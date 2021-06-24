package ro.unitbv.filip.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

public class UserDAO {
    private static UserDAO instance;
    protected EntityManager entityManager;

    public static UserDAO getInstance() {
        if (instance == null) {
            instance = new UserDAO();
        }

        return instance;
    }

    private UserDAO() {
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
     * Finds user in database based on the entered username
     *
     * @param name username to-be-logged-in
     * @return List<user> a list containing the user with the entered username
     */
    public List<User> findByName(String name){
        Query q = entityManager.createQuery("FROM " + User.class.getName() + " WHERE userName=:nm");
        q.setParameter("nm", name);

        return q.getResultList();
    }

    public void closeEntityManager(){
        entityManager.close();
    }
}
