	package com.example.product.entity;
	
	import java.sql.Timestamp;
	
	import org.hibernate.annotations.CreationTimestamp;
	
	import com.fasterxml.jackson.annotation.JsonIgnore;
	
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
	
	@Entity
	@Table(name = "Messages")
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public class Message {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		@Column(name = "messageID")
		private int messageID;
	
		@Column(name = "message_text")
		private String message_text;
	
		@Column(name = "timestamp")
		@CreationTimestamp
		private Timestamp timestamp;
	
		@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		@JoinColumn(name = "senderID", referencedColumnName = "userID")
		@JsonIgnore
		private Users senderID;
	
		@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
		@JoinColumn(name = "receiverID", referencedColumnName = "userID")
		@JsonIgnore
		private Users receiverID;
	}