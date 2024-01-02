package com.algowebpro.electronic.store.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name="orders")
public class Order {

	@Id
	private String orderId;
	
	//PENDING,DISPATHCED,DELIVERD
	//enum
	private String orderStatus;
	
	//NOT-PAID,PAID
	//enum
	//boolean - false=> NOT-PAID TRUE=> PAID
	private String paymentStatus;
	
	private int orderAmount;
	
	@Column(length = 1000)
	private String billingAddress;
	
	private String bilingPhone;
	
	private String billingName;
	
	private Date orderedDate;
	
	private Date deliveredDate;
	
	//user
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<OrderItem> orderItems = new ArrayList<>();
}
