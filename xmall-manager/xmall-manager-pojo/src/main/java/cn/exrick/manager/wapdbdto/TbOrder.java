package cn.exrick.manager.wapdbdto;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tb_order", indexes = { @Index(name = "create_time", columnList = "create_time"),
        @Index(name = "buyer_nick", columnList = "buyer_nick"),
        @Index(name = "status", columnList = "status"),
        @Index(name = "payment_type", columnList = "payment_type")
})
public class TbOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false, updatable = false)
    public String orderId;
    @Column(name = "payment", nullable = false)
    public BigDecimal payment;
    @Column(name = "payment_type", nullable = false)
    public Integer paymentType;
    @Column(name = "post_fee", nullable = false)
    public BigDecimal postFee;
    @Column(name = "status", nullable = false)
    public Integer status;
    @Column(name = "create_time", nullable = false)
    public Date createTime;
    @Column(name = "update_time", nullable = false)
    public Date updateTime;
    @Column(name = "payment_time", nullable = false)
    public Date paymentTime;
    @Column(name = "consign_time", nullable = false)
    public Date consignTime;
    @Column(name = "end_time", nullable = false)
    public Date endTime;
    @Column(name = "close_time", nullable = false)
    public Date closeTime;
    @Column(name = "shipping_name", nullable = false)
    public String shippingName;
    @Column(name = "shipping_code", nullable = false)
    public String shippingCode;
    @Column(name = "user_id", nullable = false)
    public Long userId;
    @Column(name = "buyer_message", nullable = false)
    public String buyerMessage;
    @Column(name = "buyer_nick", nullable = false)
    public String buyerNick;
    @Column(name = "buyer_comment", nullable = false)
    public Boolean buyerComment;

    @PrePersist
    public void preInsert() {
        Date date = new Date();
        createTime = date;
        updateTime = date;
    }

    // hook for pre-update:
    @PreUpdate
    public void preUpdate() {
        Date date = new Date();
        updateTime = date;
    }

}
