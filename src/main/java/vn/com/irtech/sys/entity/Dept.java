package vn.com.irtech.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:  Admin
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_dept")
@ToString
public class Dept implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer pid;

    private String name;

    /**
     * Whether to expand, 0 does not expand, 1 expands
     */
    private Integer open;

    private String remark;

    private String address;

    /**
     * Whether it is available, 0 is not available, 1 is available
     */
    private Integer available;

    /**
     * Sort code
     */
    private Integer ordernum;

    private Date createtime;


}
