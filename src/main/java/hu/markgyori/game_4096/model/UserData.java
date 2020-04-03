package hu.markgyori.game_4096.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Data;

@Entity
@Data
public class UserData {
	@Id
	@Column(name="id", nullable=false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="score")
	private int score;
	
	@Column(name="name")
	private String name;
	
	@Column(name="time")
	private int time;

	@Transient
	private int index;
	
	public UserData() {
		this.index = 0;
	}
	
	public final StringProperty timeProperty() {
		SimpleStringProperty timeProp = new SimpleStringProperty(this, "timeProp");
		timeProp.set(String.format("%02d:%02d", (int)Math.floor(this.time / 60), this.time % 60));
		return timeProp;
	}
	
}