package com.sm.democrud.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

//import org.hibernate.annotations.Entity;
//import org.springframework.data.annotation.Id;
import javax.persistence.Id;

@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String albumNm;
    
    private String albumImg;
    
    private String artistNm;

    private String sampleAu;

    private String dateRel;
    
    private String price;

    
    public Album() {}

    public Album(String albumNm, String albumImg, String artistNm, String sampleAu, String dateRel, String price) 
    {
        this.albumNm = albumNm;
        this.albumImg = albumImg;
        this.artistNm = artistNm;

        this.sampleAu = sampleAu;
        this.dateRel = dateRel;
        this.price = price;
    }
    
	public void setId(long id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public String getAlbumNm() {
		return albumNm;
	}

	public void setAlbumNm(String albumNm) {
		this.albumNm = albumNm;
	}

	public String getAlbumImg() {
		return albumImg;
	}

	public void setAlbumImg(String albumImg) {
		this.albumImg = albumImg;
	}

	public String getArtistNm() {
		return artistNm;
	}

	public void setArtistNm(String artistNm) {
		this.artistNm = artistNm;
	}

	public String getSampleAu() {
		return sampleAu;
	}

	public void setSampleAu(String sampleAu) {
		this.sampleAu = sampleAu;
	}

	public String getDateRel() {
		return dateRel;
	}

	public void setDateRel(String dateRel) {
		this.dateRel = dateRel;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
}
