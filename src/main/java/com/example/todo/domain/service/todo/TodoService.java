package com.example.todo.domain.service.todo;

import java.util.Collection;

import com.example.todo.domain.model.Todo;

public interface TodoService {
	
    /**
     * Todoの全件取得
     * @return Collection<Todo>
     */
    Collection<Todo> findAll();

    /**
     * Todoの新規作成
     * @param todo
     * @return　Todo
     */
    Todo create(Todo todo);

    /**
     * Todoの完了
     * @param todoId
     * @return Todo
     */
    Todo finish(String todoId);

    /**
     * Todoの削除
     * @param todoId
     */
    void delete(String todoId);
}
