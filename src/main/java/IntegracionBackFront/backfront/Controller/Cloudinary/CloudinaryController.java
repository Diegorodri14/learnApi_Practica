package IntegracionBackFront.backfront.Controller.Cloudinary;

import IntegracionBackFront.backfront.Services.Cloudinary.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;


@RestController
@RequestMapping ("/api/image")
@CrossOrigin
public class CloudinaryController {

    @Autowired
    private final CloudinaryService service;

    public CloudinaryController(CloudinaryService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            //Llamamos al servicio para subir la imagen y ontener la URL
            String imageURL = service.uploadImage(file);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "url", imageURL
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir la imagen");
        }
    }

    @PostMapping("/upload-to-folder")
    public ResponseEntity<?> uploadImageToFolder(
            @RequestParam("image") MultipartFile file,
            @RequestParam String folder
    ) {
        try {
            String imageURL = service.uploadImage(file, folder);
            return ResponseEntity.ok(Map.of(
                    "message", "Imagen subida exitosamente",
                    "url", imageURL
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error al subir laa imagen");
        }
    }
}