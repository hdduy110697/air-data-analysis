#include <MQUnifiedsensor.h>
#include <SoftwareSerial.h>
#include <ArduinoJson.h>

SoftwareSerial s(5, 6); // [ 5(RX) - 6(TX) ] [TX - RX]
StaticJsonBuffer <1000> jsonBuffer;
JsonObject& root = jsonBuffer.createObject();

#define pin A0 //Analog input 0 of your arduino
#define type 7 //MQ7

MQUnifiedsensor MQ7(pin, type);
float H2, LPG, CH4, CO, Alcohol;


float m = -0.301;
float b = 0.595;

///////////////////////////////////////////////////////////////////////////////////////////////////
#define USE_AVG   //calculate average value in N mesared values

// Arduino pin numbers.
const int sharpLEDPin = 2;   // Arduino digital pin 7 connect to sensor LED.
const int sharpVoPin = A1;   // Arduino analog pin 5 connect to sensor Vo.

// For averaging last N raw voltage readings.
#ifdef USE_AVG
#define N 100
static unsigned long VoRawTotal = 0;
static int VoRawCount = 0;
#endif // USE_AVG

// Set the typical output voltage in Volts when there is zero dust. 
static float Voc = 0.6;  //Voc is 0.6 Volts for dust free acordind sensor spec
static float VocT = 0.6;  //Self calibration overvrite the Coc with minimum measered value - this variable you need to have the treshold for the sensor  (facturi calibration)

// Use the typical sensitivity in units of V per 100ug/m3.
const float K = 0.5;
// Helper functions to print a data value to the serial monitor.
void printValue(String text, unsigned int value, bool isLast = false) {
  Serial.print(text);
  Serial.print("=");
  Serial.print(value);
  if (!isLast) {
    Serial.print(", ");
  }
}
void printFValue(String text, float value, String units, bool isLast = false) {
  Serial.print(text);
  Serial.print("=");
  Serial.print(value);
  Serial.print(units);
  if (!isLast) {
    Serial.print(", ");
  }
    else {
    Serial.println();
  }
}

/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
void setup() { 
  Serial.begin(9600); 
  MQ7.inicializar();
  s.begin(115200);
///////////////////////////////////////////////////////////////////////////////////////////////
 // Set LED pin for output.
  pinMode(sharpLEDPin, OUTPUT);
  
  // Start the hardware serial port for the serial monitor.
  Serial.begin(9600);
  
  // Wait two seconds for startup.
  delay(2000);
  Serial.println("");
  Serial.println("GP2Y1014AU0F Demo");
  Serial.println("=================");
  VocT = Voc; //corect the VocT (to have alwas data for the initioal Voc
} 
 
void loop() {
   
  MQ7.update();
  float CO =  MQ7.readSensor("CO");
  float H2 =  MQ7.readSensor("H2");
  float CH4 =  MQ7.readSensor("CH4");

  
//  Serial.print("Volt: ");Serial.print(MQ7.getVoltage(false));Serial.println(" V"); 
//  Serial.print("R0: ");Serial.print(MQ7.getR0());Serial.println(" Ohm"); 
//  Serial.print("CO: ");Serial.print(CO,2);Serial.println(" ppm");
//  Serial.print("H2: ");Serial.print(H2,2);Serial.println(" ppm");
//  Serial.print("CH4: ");Serial.print(CH4,2);Serial.println(" ppm");
//  delay(3000);
////////////////////////////////////////////////////////////////////////////
/////// do luong CO2////////////////
  float sensor_volt; 
  float RS_air; 
  float ratio; 
 //-Replace the name "R0" with the value of R0 in the demo of First Test -/ 
  float R0 = 76.63; 
 
  int sensorValue = analogRead(A4); 
  sensor_volt = ((float)sensorValue / 1023.0) * 5.0; 
  RS_air = ((5.0*10.0)/sensor_volt)-10.0; // Depend on RL on yor module 
  ratio = RS_air / R0; // ratio = RS/R0 
 //------------------------------------------------------------/ 
  double PPM_log = (log10(ratio)- b )/m;
  double PPM = pow(10,PPM_log);
  double percentPPM = PPM/10000;
// // Serial.print("sensor_volt = "); 
//  Serial.println(sensor_volt); 
//  Serial.print("RS_ratio = "); 
//  Serial.println(RS_air); 
//  Serial.print("Rs/R0 = "); 
//  Serial.println(ratio);
//  Serial.print("PPM = ");
//  Serial.println(percentPPM);
// 
//  Serial.print("\n\n");
//  delay(4000);
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 // Turn on the dust sensor LED by setting digital pin LOW.
  digitalWrite(sharpLEDPin, LOW);

  // Wait 0.28ms before taking a reading of the output voltage as per spec.
  delayMicroseconds(280);

  // Record the output voltage. This operation takes around 100 microseconds.
  int VoRaw = analogRead(sharpVoPin);
  
  // Turn the dust sensor LED off by setting digital pin HIGH.
  digitalWrite(sharpLEDPin, HIGH);

  // Wait for remainder of the 10ms cycle = 10000 - 280 - 100 microseconds.
  delayMicroseconds(9620);
  
  // Print raw voltage value (number from 0 to 1023).
  #ifdef PRINT_RAW_DATA
  printValue("VoRaw", VoRaw, true);
  Serial.println("");
  #endif // PRINT_RAW_DATA
  
  // Use averaging if needed.
  float Vo = VoRaw;
  #ifdef USE_AVG
  VoRawTotal += VoRaw;
  VoRawCount++;
  if ( VoRawCount >= N ) {
    Vo = 1.0 * VoRawTotal / N;
    VoRawCount = 0;
    VoRawTotal = 0;
  } else {
    return;
  }
  #endif // USE_AVG

  // Compute the output voltage in Volts.
  Vo = Vo / 1024.0 * 3.3;
  printFValue("Vo", Vo*1000.0, "mV");
  printFValue("Voc", Voc*1000.0, "mV"); // Serial print the runtime min value for the Voc - minimal measered value of the Voc during the runtime - this is 0 dust

  //------------ Dust density calculated by Voc set in the begining - like in sensor spec  ------------
  // Calculate Dust Density in units of ug/m3 for the initial Voc (as in sensor specs)
  float dV = Vo - VocT;
  if ( dV < 0 ) {
    dV = 0;
  }
  float dustDensity = dV / K * 100.0;
  String SPT1 = ""; // add this to serial print the set Voc
  SPT1 = ("Dust Density(Voc ");
  SPT1 += VocT;
  SPT1 += (" V)");
  
  printFValue(SPT1, dustDensity, "ug/m3", false); // Print the values
  dV = 0; // Reset the dV to 0 for the next calculation
  dustDensity = 0; // Reset the dustDensity to 0 for the next calculation
  
  //------------ Dust density calculated by lowest output voltage during runtime  ------------
  // Convert to Dust Density in units of ug/m3. 
  // During runtime, the Voc value is updated dynamically whenever a lower output voltage is sense. 
  // this cover the dust sensing below the sensors specefied range from 0 to Voc (0,6V) as specified in the beginin
  dV = Vo - Voc;
  if ( dV < 0 ) {
    dV = 0;
    Voc = Vo;
  }
  dustDensity = dV / K * 100.0;
  SPT1 = "";
  SPT1 = ("Dust Density(Voc ");
  SPT1 += Voc;
  SPT1 += (" V)");
  printFValue(SPT1, dustDensity, "ug/m3", true);
//////////////////////////////////////////////////////////////////////////////////  
  Serial.print("Rs/R0 = "); 
  Serial.println(ratio);
  Serial.print("PPM = ");
  Serial.println(percentPPM);
  Serial.print("Volt: ");Serial.print(MQ7.getVoltage(false));Serial.println(" V"); 
  Serial.print("R0: ");Serial.print(MQ7.getR0());Serial.println(" Ohm"); 
  Serial.print("CO: ");Serial.print(CO,2);Serial.println(" ppm");
  Serial.print("H2: ");Serial.print(H2,2);Serial.println(" ppm");
  Serial.print("CH4: ");Serial.print(CH4,2);Serial.println(" ppm");
  Serial.print("\n\n");
  Serial.println("");
  delay(100);
  ////////////////////////////////////////////////////////
  root ["CO"] = CO;
  root ["H2"] = H2;
// root ["CH4"]= CH4;
  root ["PPM"]= percentPPM;
  root ["Dusty"]= dustDensity;
   if (s.available() > 0)
  {
    root.printTo(s);
  }
; 
}
