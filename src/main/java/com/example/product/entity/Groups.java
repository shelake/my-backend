package com.example.product.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Groups")
public class Groups {
	@Id
	@Column(name = "groupID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupID;
	@Column(name = "groupName")
	private String groupName;
	//@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "adminID")
	private Users admin;
	

}