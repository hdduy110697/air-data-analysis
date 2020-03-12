package vn.com.irtech.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:  Admin
 * @since 2019-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ToString
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginname;

    private String pwd;

    private String address;

    private Integer sex;

    private String remark;

    private Integer deptid;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hiredate;

    /**
     * Superior leader id
     */
    private Integer mgr;

    /**
     * Whether it is available, 0 is not available, 1 is available
     */
    private Integer available;

    /**
     * Sort
     */
    private Integer ordernum;

    /**
     * User type [0 super administrator, 1 administrator, 2 ordinary users]
     */
    private Integer type;

    /**
     * User avatar address
     */
    private String imgpath;

    /**
     * Salt
     */
    private String salt;

    /**
     * Leader name
     */
    @TableField(exist = false)
    private String leadername;

    /**
     * Department name
     */
    @TableField(exist = false)
    private String deptname;


}
