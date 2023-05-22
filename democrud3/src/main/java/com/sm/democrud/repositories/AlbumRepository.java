package com.sm.democrud.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sm.democrud.entities.Album;

//@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {
	List<Album> findByAlbumNm(String albumNm);
	// Test
}
