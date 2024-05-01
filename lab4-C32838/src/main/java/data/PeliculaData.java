package data;

import domain.Pelicula;
import domain.PeliculaNoExistenteException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

public class PeliculaData extends RandomAccessFile {

    private final int TAMANO_REGISTRO =48;
    private final int TAMANO_COD_PELICULA =4;
    private final int TAMANO_TITULO =40;
    private final int TAMANO_PRECIO =4;


    public PeliculaData(File file) throws FileNotFoundException {
        super(file, "rw");
    }

//
    public Pelicula buscar(int codPelicula) throws IOException, PeliculaNoExistenteException{

        super.length();
        long totalRegistros = this.length()/TAMANO_REGISTRO;

        for (int i = 0; i < totalRegistros; i++) {
            this.seek(i * totalRegistros);
            int codActual = this.readInt();
            if (codActual == codPelicula){
                byte[] tituloBytes = new byte[TAMANO_TITULO];
                this.read(tituloBytes);
                String titulo = new String(tituloBytes).trim();
                int precio = this.readInt();
                return new Pelicula(codPelicula, titulo, precio);
            }
        }

       throw new PeliculaNoExistenteException();
    }


    public Pelicula insertar(Pelicula pelicula) throws IOException {

        pelicula.setCodPelicula(getMaxCodePelicula() + 1);
        long pos = this.length();
        this.seek(pos);
        this.writeInt(pelicula.getCodPelicula());
        byte[] tituloBytes = toBytes(pelicula.getTitulo(), TAMANO_TITULO);
        this.write(tituloBytes);
        this.writeInt(pelicula.getPrecio());

        return pelicula;

    }

//    public void insertarEmpleado(Empleado empInsertar)
//            throws IOException, EmpleadoExistenteException  {
//        boolean encontrado = this.buscar(empInsertar.getIdEmpleado());
//        if (encontrado)
//            throw new EmpleadoExistenteException();
//        else{
//            boolean insertado = false;
//            int totalRegistros = (int)(this.length()/TAMANO_REGISTRO);
//            int numReg=0;
//            while(numReg<totalRegistros && !insertado){
//                this.seek(numReg * TAMANO_REGISTRO);
//                this.skipBytes(TAMANO_ID_EMPLEADO);
//                String nombreActual =
//                        readString(TAMANO_NOMBRE, this.getFilePointer());
//                if(empInsertar.getNombre().compareTo(nombreActual)<=0){
//                    this.setLength(this.length()+ TAMANO_REGISTRO);
//                    // mover los registros hacia el final
//                    for (int i= totalRegistros-1; i>=numReg; i--){
//                        this.seek(i*TAMANO_REGISTRO);
//                        byte[] registroX = new byte[TAMANO_REGISTRO];
//                        this.readFully(registroX);
//                        // USUARIOS CONCURRENTES.. HAY QUE TENER MAS CUIDADO
//                        this.write(registroX);
//                    }// for
//                    // Guardar el nuevo registro
//                    this.seek(numReg * TAMANO_REGISTRO);
//                    this.writeInt(empInsertar.getIdEmpleado());
//                    this.write(
//                            toBytes(empInsertar.getNombre(),
//                                    TAMANO_NOMBRE));
//                    this.write(
//                            toBytes(empInsertar.getApellidos(),
//                                    TAMANO_APELLIDOS));
//                    insertado = true;
//                }else ++numReg;
//            }//while
//            if(!insertado){
//                this.setLength(this.length()+ TAMANO_REGISTRO);
//                this.seek(this.length()-TAMANO_REGISTRO);
//                this.writeInt(empInsertar.getIdEmpleado());
//                this.write(
//                        toBytes(empInsertar.getNombre(),
//                                TAMANO_NOMBRE));
//                this.write(
//                        toBytes(empInsertar.getApellidos(),
//                                TAMANO_APELLIDOS));
//            }
//        }//else
//
//    }//insertarEmpleado


    public List<Pelicula> findAll(){

        return null;
    }

    public Pelicula eliminar(int codPelicula) throws IOException, PeliculaNoExistenteException{

        Pelicula pelicula = buscar(codPelicula);
        long pos = (codPelicula - 1) * TAMANO_REGISTRO;
        this.seek(pos);
        this.writeInt(0);

        return pelicula;
    }

    private int getMaxCodePelicula() throws IOException{

        long numRegistros = this.length()/TAMANO_REGISTRO;
        int maxCodePelicula = 0;

        for (int i = 0; i < numRegistros; i++) {

            long pos = 1 * TAMANO_REGISTRO;

            this.seek(pos);

            int code = this.readInt();
            if (code > maxCodePelicula){
                maxCodePelicula = code;
            }
        }

        return maxCodePelicula;
    }


    private byte[] toBytes(String dato, int tamanoString){
        byte[] datos = new byte[tamanoString];
        if (dato.length() > tamanoString){
            byte[] temp = dato.getBytes();
            for (int i = 0; i < tamanoString; i++) {
                datos[i] =temp[i];
            }
        }else{
            byte temp[] = dato.getBytes();
            int i = 0;
            for (byte c : temp) {
                datos[i++] = c;
            }

        }
        return datos;
    }

}
