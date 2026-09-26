package jpql;


import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Objects;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List<Address> result = em.createQuery("select o.address from Order m", Address.class).getResultList();
            List<Member> result2 = em.createQuery("SELECT m.username, m.age FROM Member m", Member.class).getResultList();

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }

        em.close();
        emf.close();
    }
}
