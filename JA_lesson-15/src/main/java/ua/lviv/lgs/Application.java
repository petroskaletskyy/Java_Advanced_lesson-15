package ua.lviv.lgs;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class Application {

	public static void main(String[] args) {

		Configuration config = new Configuration();
		config.configure("hibernate.cfg.xml");

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties())
				.build();

		Session session = config.buildSessionFactory(serviceRegistry).openSession();

		Post post = new Post("First post");
		Post post2 = new Post("Second post");

		Comment com1 = new Comment();
		com1.setAuthorName("Author 1");
		com1.setPost(post);
		Comment com2 = new Comment();
		com2.setAuthorName("Author 2");
		com2.setPost(post2);
		Comment com3 = new Comment();
		com3.setAuthorName("Author 3");
		com3.setPost(post2);
		Comment com4 = new Comment();
		com4.setAuthorName("Author 4");
		com4.setPost(post);

		Set<Comment> commentsFromPost1 = new HashSet<>();
		commentsFromPost1.add(com1);
		commentsFromPost1.add(com4);
		
		Set<Comment> commentsFromPost2 =new HashSet<>();
		commentsFromPost2.add(com2);
		commentsFromPost2.add(com3);

		post.setComments(commentsFromPost1);
		post2.setComments(commentsFromPost2);

		// save to DB
		Transaction tr = session.beginTransaction();
		session.save(post);
		session.save(post2);
		tr.commit();

		// read from DB
		Post postFromDB = (Post) session.get(Post.class, 1);
		System.out.println(postFromDB + "-->" + postFromDB.getComments());

		Post post2FromDB = (Post) session.get(Post.class, 2);
		System.out.println(post2FromDB + "-->" + post2FromDB.getComments());

		Comment commentFromDB = (Comment) session.get(Comment.class, 3);
		System.out.println(commentFromDB + "-->" + commentFromDB.getPost());

		Comment comment2FromDB = (Comment) session.get(Comment.class, 4);
		System.out.println(comment2FromDB + "-->" + commentFromDB.getPost());

		session.close();
	}

}
