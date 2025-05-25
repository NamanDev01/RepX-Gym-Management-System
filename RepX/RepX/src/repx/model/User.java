package repx.model;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private int age;
    private String contact;
    private String membershipType;
    private Date joinDate;

    // Constructors
    public User(String name, int age, String contact, String membershipType, Date joinDate) {
        this.name = name;
        this.age = age;
        this.contact = contact;
        this.membershipType = membershipType;
        this.joinDate = joinDate;
    }

    public User() {} // Default constructor

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getMembershipType() { return membershipType; }
    public void setMembershipType(String membershipType) { this.membershipType = membershipType; }

    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
}
