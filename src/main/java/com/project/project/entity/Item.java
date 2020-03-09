package com.project.project.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "ITEM")
public class Item {

    public Item() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Sequence")
    @GenericGenerator(name = "Sequence", strategy = "com.project.project.entity.Sequence")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "CONTAINED_IN")
    private Box box;

    @Column(name = "CONTAINED_IN", insertable = false, updatable = false)
    private int refBoxId;

    @Column(name = "COLOR")
    private String color;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getRefBoxId() {
        return refBoxId;
    }

    public void setRefBoxId(int refBoxId) {
        this.refBoxId = refBoxId;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", box=" + box
                + ", refBoxId=" + refBoxId
                + ", color='" + color + '\''
                + '}';
    }
}
