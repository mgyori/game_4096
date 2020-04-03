package hu.markgyori.game_4096.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserData {
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	@Column(name="score")
	private int score;
	
	@Column(name="name")
	private String name;
	
	@Column(name="time")
	private int time;
}