package edu.pucmm.eict.ormjpa.servicios;

import edu.pucmm.eict.ormjpa.entidades.Producto;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductoServices extends GestionDb<Producto> {

    private static ProductoServices instancia;

    private ProductoServices() {
        super(Producto.class);
    }

    public static ProductoServices getInstancia() {
        if (instancia == null) {
            instancia = new ProductoServices();
        }
        return instancia;
    }

    public List<Producto> listarPaginado(int pagina, int tamano) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("select p from Producto p order by p.id", Producto.class)
                    .setFirstResult(pagina * tamano)
                    .setMaxResults(tamano)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long contar() {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery("select count(p) from Producto p", Long.class).getSingleResult();
        } finally {
            em.close();
        }
    }
}
