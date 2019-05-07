package rs.raf.projekat.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import rs.raf.projekat.demo.model.Post;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import rs.raf.projekat.demo.model.QPost;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post>, QuerydslBinderCustomizer<QPost> {

    List<Post> findTop50ByOwner_IdInOrderByCreatedTime (List<Long> ids);

    @Override
    default void customize(QuerydslBindings bindings, QPost root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
