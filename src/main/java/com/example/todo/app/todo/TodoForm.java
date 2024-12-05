package com.example.todo.app.todo;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class TodoForm implements Serializable {
	private static final long serialVersionUID = 1L;

	public static interface TodoCreate {};

    public static interface TodoFinish {};
    
	@NotNull(groups = { TodoCreate.class})
	@Size(min = 1, max = 30)
    private String todoTitle;
	
	@NotNull(groups = {TodoFinish.class})
	private String todoId;
}
