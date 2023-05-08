package app.Inksight;

public class reviews {
        Libro libro;
        String descripcion;
        int puntuacion;

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public void setLibro(Libro libro) {
            this.libro = libro;
        }

        public void setPuntuacion(int puntuacion) {
            this.puntuacion = puntuacion;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public Libro getLibro() {
            return libro;
        }

        public int getPuntuacion() {
            return puntuacion;
        }


}
