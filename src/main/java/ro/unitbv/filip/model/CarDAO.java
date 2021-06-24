package ro.unitbv.filip.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

public class CarDAO {
    private static CarDAO instance;
    protected EntityManager entityManager;

    public static CarDAO getInstance() {
        if (instance == null) {
            instance = new CarDAO();
        }

        return instance;
    }

    private CarDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("newcarapp");

        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    /**
     * This method returns the car list belonging to the logged in user
     *
     * @param ownerId The id of the owner
     * @return A list of type Car
     */
    public List<Car> findByOwner(int ownerId) {
        Query q = entityManager.createQuery("FROM " + Car.class.getName() + " WHERE ownerId=:id");
        q.setParameter("id", ownerId);

        return q.getResultList();
    }

    @Transactional
    public void deleteByRegistrationPlate(String registrationPlate) {
        entityManager.getTransaction().begin();

        Query q = entityManager.createQuery("FROM " + Car.class.getName() + " WHERE registrationPlate=:regPlate");
        q.setParameter("regPlate", registrationPlate);
        Car car = (Car) q.getResultList().get(0);

        entityManager.remove(car);
        entityManager.flush();

        entityManager.getTransaction().commit();

    }

    @Transactional
    public void add(Car car) {
        entityManager.getTransaction().begin();

        entityManager.persist(car);
        entityManager.flush();

        entityManager.getTransaction().commit();
    }
}
