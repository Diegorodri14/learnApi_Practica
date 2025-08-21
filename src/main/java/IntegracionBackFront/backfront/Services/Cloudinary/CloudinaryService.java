package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    // 1. Constante para definir eel tamaño maximo permitido para los archivos (5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // 2. Extenciones de archivo permitidas para subir a Cloudinary
    private static final String [] ALLOWED_EXTESIONS = {".jpg", ".png", ".jpeg"};

    // 3. Cliente de Cloudinary inyectando como dependecia
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Subir imagenes a  la raiz  de Cloudinary
    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);
        Map<?, ?> uploadResult = cloudinary.uploader()
                .upload(file.getBytes(), ObjectUtils.asMap(
                       "resource_type", "auto",
                       "quality", "auto:good"
                ));
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder)throws IOException{
        validateImage(file);
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFilename = "img_" + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
                "folder", folder,       //Carpeta de destinos
                "public", uniqueFilename,       //Nombre unico para el archivo
                "use_filename", false,          //No usar el nombre original
                "unique_filename", false,       //No generar nombre unico(Ya lo hicimos)
                "overwrite", false,             //No sobreescribir archivos existentes
                "resource_type", "auto",
                "quality", "auto:good"

        );
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),options);
        return (String) uploadResult.get("secure_url");
    }


    private void validateImage(MultipartFile file) {
        // 1. Verificar si el archivo esta vacio
        if (file.isEmpty()) throw new IllegalArgumentException("El archivo no puede ir vacio");// Verificar si el archivo esta vacio
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("El tamaño del archivo no puede exceder los 5MB");//Verificar si el tamaño del archivo excede el limite permitido
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw new IllegalArgumentException("Nombre de archivo no valido");// Valida el nombre original del archivo
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTESIONS).contains(extension)) throw new IllegalArgumentException("Solo se permiten archivos jpg, jpeg, o png");
        if (!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe de ser una imagen valida");
    }

    //Subir imagenes a una carpeta de Cloudinary
}
