package com.alumnositm.todo.dtos.request;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CreateTodoRequestTest 
{
    @Test
    void testConstructorAndGetterSetter()
    {
        String title = " Prueba de ToDo";
        String descripcion = "Pruebas de cuerpo de la descripción";
        String descripcionError = " Pruebas de cuerpo de la descripción ";
        CreateTodoRequest createTodoRequest = new CreateTodoRequest(title, descripcion);
        //assertEquals(descripcion, createTodoRequest.getDescription());
        assertEquals(descripcionError, createTodoRequest.getDescription());
        //Compara "descripcion" con "descripcionError"
        //Como tienen espacios al principio y final del String, no son iguales y da error
        assertEquals(title, createTodoRequest.getTitle());
    }

    //Instanciar valores
    //Modificar los valores
    //Comparar los valores modificados en la instancia
    @Test
    void testConstructorSetterModify()
    {
        String title = "Titulo";
        String description = "Hola mundo";
        String titleModify = "Titulo modificado";
        String descriptionModify = "Hola mundo modificado";
        
        CreateTodoRequest createTodoRequest = new CreateTodoRequest(title, description);
        createTodoRequest.setTitle(titleModify);
        createTodoRequest.setDescription(descriptionModify);
        assertEquals(titleModify, createTodoRequest.getTitle());
        assertEquals(descriptionModify, createTodoRequest.getDescription());

        assertNotEquals(title, createTodoRequest.getTitle());
        assertNotEquals(description, createTodoRequest.getDescription());
    }
}