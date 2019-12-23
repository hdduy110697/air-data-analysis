#include <SoftwareSerial.h>
//[D6(RX) - D5(TX)] - [TX -RX]
#include <ArduinoJson.h>
#include <ThingSpeak.h>
#include <ESP8266WiFi.h>
SoftwareSerial s(D6,D5);
//----------------  Fill in your credentails   ---------------------
char ssid[] = "thanhhuyen";             // your network SSID (name), 
char pass[] = "thanhhuyen";         // your network password, password
unsigned long myChannelNumber = 926632;  // Replace the 0 with your channel number, channel của bạn
const char * myWriteAPIKey = "BETY0YSTF8RXDXGO";    // Paste your ThingSpeak Write API Key between the quotes, write API key
//------------------------------------------------------------------
WiFiClient  client;

void setup() {
  // Initialize Serial port
  Serial.begin(9600);
  s.begin(115200);
  while (!Serial) continue;
 WiFi.mode(WIFI_STA);
  ThingSpeak.begin(client);
   
   if (WiFi.status() != WL_CONNECTED) {
    Serial.print("Attempting to connect to SSID: ");
    //    Serial.println(SECRET_SSID);
    while (WiFi.status() != WL_CONNECTED) {
      WiFi.begin(ssid, pass);
      Serial.print(".");
      delay(5000);
    }
    Serial.println("\nConnected.");
  }
}
 
void loop() {
 StaticJsonBuffer <1000> jsonBuffer;
 JsonObject&root = jsonBuffer.parseObject(s);
  if (root == JsonObject::invalid())
     return;
// if (WiFi.status() != WL_CONNECTED) {
//    Serial.print("Attempting to connect to SSID: ");
//    //    Serial.println(SECRET_SSID);
//    while (WiFi.status() != WL_CONNECTED) {
//      WiFi.begin(ssid, pass);
//      Serial.print(".");
//      delay(5000);
//    }
//    Serial.println("\nConnected.");
//  }
  Serial.println("JSON received and parsed");
  root.prettyPrintTo(Serial);
  
  float data3=root["CO"];
  float data4=root["H2"];
  float data5=root["PPM"];
  float data6=root["CH4"];
  float data7=root["Dusty"];
  
  Serial.print(data3);
  Serial.print(data4);
  Serial.print(data5);
  Serial.print(data6);
  Serial.print(data7);
  Serial.println("");
  Serial.println("---------------------xxxxx--------------------");
  
  float a =  ThingSpeak.setField(1, data3); //setField(field, value)
  float b =  ThingSpeak.setField(2, data4); //setField(field, value)
  float c =  ThingSpeak.setField(3, data5); //setField(field, value)
  float d =  ThingSpeak.setField(4, data6); //setField(field, value)
  float e =  ThingSpeak.setField(5, data7); //setField(field, value)

  int z = ThingSpeak.writeFields(myChannelNumber, myWriteAPIKey);
 // Kiểm tra return code
  if (z == 200) {
    Serial.println("Channel update successful.");
  }
  else {
    Serial.println("Problem updating channel. HTTP error code " + String(z));
  }
  delay(20000); // Chờ 20s trước khi gửi dữ liệu mới
}
