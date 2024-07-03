package com.kesm.backend.views;

import com.kesm.backend.entities.Address;
import com.kesm.backend.entities.Contact;
import com.kesm.backend.entities.Person;
import com.kesm.backend.repositories.ContactRepository;
import com.kesm.backend.repositories.ContactTypeRepository;
import com.kesm.backend.repositories.PersonRepository;
import com.kesm.backend.services.AddressService;
import com.kesm.backend.services.PersonService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Locale;

@SpringComponent
@UIScope
public class PersonView extends VerticalLayout implements KeyNotifier {
	private final PersonRepository repository;
	private final PersonService personService;
	private final AddressService addressService;
	private final ContactRepository contactRepository;
	private final ContactTypeRepository contactTypeRepository;
	private Person person;
	private Address permAddress;
	private Address tempAddress;
	private final Grid<Contact> permGrid = new Grid<>(Contact.class, false);
	private final Grid<Contact> tempGrid = new Grid<>(Contact.class, false);

	TextField firstName = new TextField( "First name" );
	TextField lastName = new TextField( "Last name" );
	TextField idCard = new TextField( "Identity Card Number" );
	TextField taxNumber = new TextField( "Tax Number" );
	TextField birthPlace = new TextField( "Birth Place" );
	DatePicker birthDate = new DatePicker( "Birth Date:" );

	Button save = new Button( "Save" );
	Button cancel = new Button( "Cancel" );
	Button delete = new Button( "Delete" );

	HorizontalLayout actions = new HorizontalLayout( save, cancel, delete );

	Binder<Person> binder = new Binder<>( Person.class );
	Binder<Address> permAddressBinder = new Binder<>( Address.class );
	Binder<Address> tempAddressBinder = new Binder<>( Address.class );

	@Setter
    private ChangeHandler changeHandler;

	@Autowired
	public PersonView(PersonRepository repository, PersonService personService, AddressService addressService,
					  ContactRepository contactRepository, ContactTypeRepository contactTypeRepository ) {
		this.repository = repository;
		this.personService = personService;
		this.addressService = addressService;
		this.contactRepository = contactRepository;
		this.contactTypeRepository = contactTypeRepository;
		this.birthDate.setLocale( new Locale("hu", "HU") );

		add(
			new HorizontalLayout(
				new VerticalLayout(
						new HorizontalLayout( firstName, lastName ),
						new HorizontalLayout( birthPlace, birthDate ),
						new HorizontalLayout( idCard, taxNumber ) ),
				getAddressFields( this.permAddressBinder, this.permGrid, true ),
				getAddressFields( this.tempAddressBinder, this.tempGrid, false ) ),
			actions );

		bindPersonFields();

		setSpacing(true);

		addKeyPressListener( Key.ENTER, e -> save() );

		save.addClickListener( e -> save() );
		delete.addClickListener( e -> delete() );
		cancel.addClickListener( e -> editPerson( person ) );

		setVisible( false );
	}

	void delete() {
		repository.delete( person );
		changeHandler.onChange();
	}

	void save() {
		personService.save( person );
		addressService.save( this.permAddress, null );
		addressService.save( this.tempAddress, null );

		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editPerson( Person entity ) {
		if ( entity == null ) {
			setVisible( false );
			return;
		}

		final boolean persisted = entity.getId() != null;

		this.person = persisted ? repository.findById( entity.getId() ).get() : entity;

		cancel.setVisible( persisted );
		binder.setBean( this.person );

		setPersonAddresses();

		setVisible( true );
		firstName.focus();
	}

	private void setPersonAddresses() {
		this.permAddress = this.person.getAddresses().stream()
				.filter( a -> !Boolean.TRUE.equals( a.getTemporary() ) )
				.findFirst()
				.orElse( new Address() );

		this.permAddressBinder.setBean( this.permAddress );

		if ( this.permAddress != null && this.permAddress.getContacts() != null ) {
			this.permGrid.setItems( this.permAddress.getContacts() );
		} else {
			this.tempGrid.setItems( Collections.emptyList() );
		}

		this.tempAddress = this.person.getAddresses().stream()
				.filter( a -> Boolean.TRUE.equals( a.getTemporary() ) )
				.findFirst()
				.orElse( new Address() );

		this.tempAddressBinder.setBean( this.tempAddress );

		if ( this.tempAddress != null && this.tempAddress.getContacts() != null ) {
			this.tempGrid.setItems( this.tempAddress.getContacts() );
		} else {
			this.tempGrid.setItems( Collections.emptyList() );
		}
	}

	private void bindPersonFields() {
		binder.bind(firstName, Person::getFirstName, Person::setFirstName);
		binder.bind(lastName, Person::getLastName, Person::setLastName);
		binder.bind(idCard, Person::getIdCard, Person::setIdCard);
		binder.bind(taxNumber, Person::getTaxNumber, Person::setTaxNumber);
		binder.bind(birthPlace, Person::getBirthPlace, Person::setBirthPlace);
		binder.bind(birthDate, Person::getBirthDate, Person::setBirthDate);
	}

	private VerticalLayout getAddressFields( Binder<Address> addressBinder, Grid<Contact> grid, boolean perm ) {
		TextField city = new TextField( "City" );
		TextField postalCode = new TextField( "Postal Code" );
		TextField street = new TextField( "Street" );
		ContactDialog dialog = new ContactDialog( this.contactRepository, this.contactTypeRepository );

		addressBinder.bind( city, Address::getCity, Address::setCity );
		addressBinder.bind( street, Address::getStreet, Address::setStreet );
		addressBinder.bind( postalCode, Address::getPostalCode, Address::setPostalCode );

		grid.setHeight( "100px" );
		grid.addColumn( Contact::getContact ).setHeader( "Contact" );
		grid.addColumn( c -> c.getContactType().getTitle() ).setHeader( "Type" );
		grid.asSingleSelect().addValueChangeListener( e ->
				dialog.editContact( e.getValue(), grid, perm ? this.permAddress : this.tempAddress ) );
		Button btnDialog = new Button("New", e ->
				dialog.editContact( null, grid, perm ? this.permAddress : this.tempAddress ) );

		return new VerticalLayout(
				new HorizontalLayout( city, postalCode ),
				street, grid, btnDialog );
	}
}