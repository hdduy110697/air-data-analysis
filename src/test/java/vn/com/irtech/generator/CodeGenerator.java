package vn.com.irtech.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;
public class CodeGenerator {

    /**
     * <p>
     * Read console content
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("Please enter " + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("Please enter the correct " + tip + "！");
    }

    public static void main(String[] args) {
        // Code generator
        AutoGenerator mpg = new AutoGenerator();

        // Global configuration
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("IRTech");
        gc.setOpen(false);  //Whether to open the folder where the code is located after the code generation is completed
        // gc.setSwagger2(true); Entity attribute Swagger2 annotation
        mpg.setGlobalConfig(gc);

        // Data source configuration
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/warehouse?useUnicode=true&useSSL=false&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // Package configuration
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("Module name"));
        pc.setParent("vn.com.irtech");
        pc.setXml("mapper.xml");
        mpg.setPackageInfo(pc);

        // Policy configuration
        StrategyConfig strategy = new StrategyConfig();
        //Set whether field names and table names are replaced by underscores by camel case naming rules
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        //Set the parent class inherited by the generated entity class
        //strategy.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");

        //Whether to enable lombok
        strategy.setEntityLombokModel(true);

        //Whether to generate restController
        strategy.setRestControllerStyle(true);

        // Public parent
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");

        // Public fields written in the parent class
//        strategy.setSuperEntityColumns("id");

        //Set which tables to generate. If not set, all tables are generated.
        strategy.setInclude(scanner("Table name, multiple commas").split(","));
        strategy.setControllerMappingHyphenStyle(true);

        strategy.setTablePrefix(pc.getModuleName() + "_");
//        strategy.setTablePrefix("sys_");
        mpg.setStrategy(strategy);
        mpg.execute();
    }


}
