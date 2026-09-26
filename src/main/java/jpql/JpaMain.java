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
            Team team = new Team();
            team.setName("team1");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            member.setTeam(team);

            em.flush();
            em.clear();

            String query = "select m from Member m inner join m.team t";
            List<Team> result = em.createQuery(query, Team.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            tx.commit();
        } catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }

        em.close();
        emf.close();
    }
}
