package com.kesm.backend.views;

import com.kesm.backend.entities.Address;
import com.kesm.backend.entities.Contact;
import com.kesm.backend.entities.ContactType;
import com.kesm.backend.repositories.ContactRepository;
import com.kesm.backend.repositories.ContactTypeRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

@SpringComponent
@UIScope
public class ContactDialog extends Dialog {
    private final ContactRepository contactRepository;
    private Contact contact;
    private Address address;

    private Grid<Contact> grid;
    private final Binder<Contact> binder = new Binder<>( Contact.class );

    public ContactDialog(ContactRepository contactRepository, ContactTypeRepository contactTypeRepository ) {
        this.contactRepository = contactRepository;
        this.address = null;
        setHeaderTitle("New Contact");
        ComboBox<ContactType> cbContactType = new ComboBox<>("Contact Type");
        cbContactType.setItems( contactTypeRepository.findAll() );
        cbContactType.setItemLabelGenerator( ContactType::getTitle );
        TextField txtContact = new TextField("contact text");
        binder.bind(txtContact, Contact::getContact, Contact::setContact );
        binder.bind(cbContactType, Contact::getContactType, Contact::setContactType );

        VerticalLayout dialogLayout = new VerticalLayout(cbContactType, txtContact);
        add(dialogLayout);

        Button btnSave = new Button( "Save", e -> this.save() );
        Button btnDelete = new Button( "Delete", e -> this.delete() );
        Button btnCancel = new Button("Cancel", e -> close());
        getFooter().add( btnSave, btnDelete, btnCancel );
    }

    void delete() {
        if ( this.contact.getId() != null ) {
            this.address.getContacts().remove( this.contact );
            this.contactRepository.delete( contact );
        }

        grid.setItems( this.address.getContacts() );
        close();
    }

    void save() {
        if ( this.contact.getAddress() == null || this.address.getId() == null ) {
            close();
            HomeView.sendNotification( "Save address before!" );

            return;
        }

        if ( this.contact.getId() == null ) {
            this.contactRepository.save( contact );
        }

        if ( this.address.getContacts() == null ) {
            this.address.setContacts( List.of( this.contact ) );
        } else {
            long count = this.address.getContacts().stream()
                    .filter( c -> c.getId().equals( this.contact.getId() ) )
                    .count();

            if ( count > 0 ) {
                this.address.getContacts().stream()
                        .filter( c -> c.getId().equals( this.contact.getId() ) )
                        .forEach( c -> c = this.contact );
            } else {
                this.address.getContacts().add( this.contact );
            }
        }

        grid.setItems( this.address.getContacts() );
        close();
    }

    public final void editContact( Contact entity, Grid<Contact> grid, Address address ) {
        this.contact = entity != null && entity.getId() != null ?
                contactRepository.findById( entity.getId() ).get() : new Contact();

        this.address = address;
        this.contact.setAddress( this.address );
        this.binder.setBean( this.contact );
        this.grid = grid;

        open();
    }
}
