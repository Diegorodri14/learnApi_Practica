package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;

@Service
public class cloudinaryService {
    //1. Definir el tamaño de las imagenes en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2. Definir las extenciones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg",".jpeg",".png"};

    //3. Atributo de Cloudinary
    private final Cloudinary cloudinary;

    //4. Hacemos el contructor para la inyeccion de dependencias de Cloudinary
    public cloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage (MultipartFile file)throws IOException {
        validateImage(file);
    }

    private void validateImage(MultipartFile file) {
        //1. Verificaremos si el archivo esta vacio
        if (file.isEmpty()){
            throw new IllegalArgumentException("El archvo esta vacio");
        }

        //2. Verificar si el tamaño de la imagen excede el limite permitido
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamaño del archivo no debe ser mayor a 5MB");
        }

        //#. Verificar el nombre original del archivo
        String OriginalFilename = file.getOriginalFilename();
        if (OriginalFilename == null){
            throw new IllegalArgumentException("Nombre de archivo invalido");
        }

        //4. Extraer y validar la extencion del archivo
        String extension = file.OriginalFilename.substring(originalFileName.lastIndexOf());
        if (!Array.asList(ALLOWED_EXTENSIONS).contains(extension)) {
            throw new IllegalArgumentException("Solo se permite archivos JPG, JPEG, PNG");
        }
    }
}
