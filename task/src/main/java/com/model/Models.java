package com.model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;



public class Models {

	private Session session;
	private EqBase eq;
	private SessionFactory sessionFactory;

	public Models() {
		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");
		config.addAnnotatedClass(EqBase.class);
		sessionFactory = config.buildSessionFactory();
		session = sessionFactory.openSession();
		eq = new EqBase();
	}
	
	// Закриття підключення до БД
	public void close() {
		session.close();
	}
	
	// Додання рівняння в БД
	public boolean setEqSentence(String equation) {
		try {
			session.beginTransaction();
			eq.setEqSentence(equation);
			session.persist(eq);
			session.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Отримання рівняння з БД за його коренем
	public List<EqBase> getEqSentence(double eqRoot) {    
        List<EqBase> eqId = search("eqRoot", eqRoot);
        return eqId;
	}
	
	// Отримання id рівняння з БД
	public String getEqId(String eqSentence) {
		List<EqBase> eqId = search("eqSentence", eqSentence);
		
        if (eqId.size() > 0) return Integer.toString(eqId.get(0).getId());
        else return "empty";
	}
	
	// Додання в БД кореня eqRoot до рівняння з id=eqId
	public boolean setEqRoot(String eqId, double eqRoot) {
        List<EqBase> eqSentence = search("id", Integer.parseInt(eqId));
        
        try {
        	session.beginTransaction();
            eqSentence.get(0).setEqRoot(eqRoot);
            session.getTransaction().commit();
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Отримання вибірки даних за параметром field зі значенням parameter
	public List<EqBase> search(String field, Object parameter) {
	    session.beginTransaction();

	    HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<EqBase> criteria = builder.createQuery(EqBase.class);
	    Root<EqBase> root = criteria.from(EqBase.class);

	    ParameterExpression<?> exp = null;
	    if (parameter instanceof String) {
	        exp = builder.parameter(String.class);
	    } else if (parameter instanceof Integer) {
	        exp = builder.parameter(Integer.class);
	    } else if (parameter instanceof Double) {
	        exp = builder.parameter(Double.class);
	    }

	    criteria.select(root)
	            .where(builder.equal(root.get(field), exp));

	    Query<EqBase> query = session.createQuery(criteria);

	    query.setParameter((ParameterExpression<Object>) exp, parameter);

	    List<EqBase> equations = query.getResultList();
	    session.getTransaction().commit();

	    return equations;
	}

}
