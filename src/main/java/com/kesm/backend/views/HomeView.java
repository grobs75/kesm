package com.kesm.backend.views;

import com.kesm.backend.entities.Person;
import com.kesm.backend.repositories.PersonRepository;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route("")
public class HomeView extends VerticalLayout {
    private final PersonRepository repository;

    final Grid<Person> grid;
    final TextField filter = new TextField( "Filter by last name" );

    public HomeView( PersonRepository repository, PersonView personView ) {
        this.repository = repository;
        this.grid = new Grid<>(Person.class);
        Button btnAddNew = new Button("New Person");

        HorizontalLayout actions = new HorizontalLayout( filter, btnAddNew);
        add( actions, grid, personView );

        grid.setHeight( "300px" );
        grid.setColumns( "lastName", "firstName" );

        filter.setValueChangeMode( ValueChangeMode.LAZY );
        filter.addValueChangeListener( e -> listPersons( e.getValue() ) );

        grid.asSingleSelect().addValueChangeListener( e -> personView.editPerson( e.getValue() ) );

        btnAddNew.addClickListener(e -> personView.editPerson( new Person() ) );

        personView.setChangeHandler(() -> {
            personView.setVisible(false);
            listPersons(filter.getValue());
        });

        listPersons(null);
    }

    private void listPersons( String filter ) {
        if ( StringUtils.hasText( filter ) ) {
            grid.setItems( repository.findByLastNameStartsWithIgnoreCase( filter ) );
        } else {
            grid.setItems( repository.findAll() );
        }
    }

    public static void sendNotification( String notificationMessage ) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div( new Text( notificationMessage ) );

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }
}
