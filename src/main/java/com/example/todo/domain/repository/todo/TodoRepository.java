package com.example.todo.domain.repository.todo;

import java.util.Collection;
import java.util.Optional;

import com.example.todo.domain.model.Todo;

/**
 * 
 */
public interface TodoRepository {
	
    /**
     * TODOの1件取得
     * @param todoId
     * @return Optional<Todo>
     */
    Optional<Todo> findById(String todoId);

    /**
     * TODOの全件取得
     * @return Collection<Todo>
     */
    Collection<Todo> findAll();

    
    /**
     * TODOの1件作成
     * @param todo
     */
    void create(Todo todo);

    /**
     * TODOの1件更新
     * @param todo
     * @return boolean
     */
    boolean update(Todo todo);

    /**
     * TODOの1件削除
     * @param todo
     */
    void delete(Todo todo);

    /**
     * 完了済みTODO件数の取得
     * @param finished
     * @return long
     */
    long countByFinished(boolean finished);
}
