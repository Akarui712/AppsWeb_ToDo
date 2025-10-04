package com.alumnositm.todo.dtos.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class CreateTodoRequest 
{
    //Se especifican las validaciones para cada variable
    //Pueden tener los dos tipos o solo uno, por eso se ponen por separado
    @NotBlank(message = "El título no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s\\-_.,!¡]+$", 
            message = "El título no puede tener espacios al inicio o al final")
    private String title;

    @NotBlank(message = "El título no puede estar vacío")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s\\-_.,!¡]+$", 
            message = "La descripción no puede tener espacios al inicio o al final")
    private String description;

    public CreateTodoRequest(String title, String description)
    {
        this.title = title;
        this.description = description;
    }
}