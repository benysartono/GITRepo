package com.sm.democrud.controllers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sm.democrud.entities.Album;
import com.sm.democrud.repositories.AlbumRepository;

@Controller
public class AlbumController {
	//public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";	
	public static String UPLOAD_DIRECTORY = "imgs/";	
	
	@Autowired
	AlbumRepository albumRepository;
	
    @GetMapping("/signup")
    public String showSignUpForm(Album album) {
    		return "add-album";
    }
    
    @PostMapping("/addalbum")
    public String addAlbum(Album album, BindingResult result, Model model, @RequestParam("albumImgFile") MultipartFile albumImgFile, @RequestParam("audioFile") MultipartFile audioFile) {
        if (result.hasErrors()) {
            return "add-album";
        }
        
        try {
        albumRepository.save(album);
        } catch(Exception e) {
        	e.printStackTrace();
        }

        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, album.getId() + "Img.jpg");
        try {
			//Files.write(fileNameAndPath, albumImgFile.getBytes());
            String fileName = album.getId() + "Img.jpg";
            FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, albumImgFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Path fileNameAndPath2 = Paths.get(UPLOAD_DIRECTORY, album.getId() + "Aud.mp3");
        try {
			//Files.write(fileNameAndPath2, audioFile.getBytes());
            String fileName = album.getId() + "Aud.mp3";
            FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, audioFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String showAlbumList(Model model) {
        model.addAttribute("albums", albumRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Album album = albumRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));

                
        model.addAttribute("album", album);
        return "update-album";
    }
    
    @PostMapping("/update/{id}")
    public String updateAlbum(@PathVariable("id") long id, Album album, 
      BindingResult result, Model model, @RequestParam("albumImgFile2") MultipartFile albumImgFile2, @RequestParam("audioFile2") MultipartFile audioFile2) {
        if (result.hasErrors()) {
            album.setId(id);
            return "update-album";
        }
        
        albumRepository.save(album);
        
        Path fileNameAndPath = Paths.get(UPLOAD_DIRECTORY, album.getId() + "Img.jpg");
        try {
			//Files.write(fileNameAndPath, albumImgFile2.getBytes());
            String fileName = album.getId() + "Img.jpg";
            FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, albumImgFile2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Path fileNameAndPath2 = Paths.get(UPLOAD_DIRECTORY, album.getId() + "Aud.mp3");
        try {
			//Files.write(fileNameAndPath2, audioFile2.getBytes());
            String fileName = album.getId() + "Aud.mp3";
            FileUploadUtil.saveFile(UPLOAD_DIRECTORY, fileName, audioFile2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return "redirect:/index";
    }
        
    @GetMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable("id") long id, Model model) {
        Album album = albumRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        albumRepository.delete(album);
        return "redirect:/index";
    }
    
}
