package lu.lusis.demo.ui.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lu.lusis.demo.backend.data.User;
import lu.lusis.demo.backend.repository.UserRepository;
import lu.lusis.demo.ui.AppLayout;
import lu.lusis.demo.ui.dataprovider.ExampleFilterDataProvider;

import javax.annotation.PostConstruct;

@PageTitle("User List")
//@Tag("users-list")
@Route(value = "users",layout = AppLayout.class)
public class UserListView extends VerticalLayout implements AfterNavigationObserver {

    private final UserRepository userRepository;


    private HorizontalLayout filter;
    private Grid<User> userGrid;

    private TextField filterFirstname;
    private TextField filterName;

    public UserListView(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void init() {

        ExampleFilterDataProvider<User, Integer> filterDataProvider = new ExampleFilterDataProvider<>(userRepository);

        filter = new HorizontalLayout();
        filter.setWidth("100%");
        filter.setAlignItems(Alignment.START);

        filterFirstname = new TextField("Prénom");
        filterName = new TextField("Nom");


        Binder<User> binder = new Binder<>(User.class);
        //  binder.bind(filterFirstname,"firstname");
        binder.forField(filterFirstname)
                .withValidator(firstname -> firstname.isEmpty()|| firstname.length() > 2,"Veuillez saisir a moins 3 caractères")
                .bind("firstname");
        //     binder.bind(filterName,"name");
        binder.forField(filterName)
                .withValidator(name -> name.isEmpty()|| name.length() > 2,"Veuillez saisir a moins 3 caractères")
                .bind("name");
        User filterUser = new User();
        binder.setBean(filterUser);


        binder.addValueChangeListener(
                event ->  {if (binder.isValid()) {filterDataProvider.setFilter(filterUser);}});



        filterFirstname.setValueChangeMode(ValueChangeMode.EAGER);
        filterName.setValueChangeMode(ValueChangeMode.EAGER);


/*
        Button button = new Button("Rechercher",
                e -> filterDataProvider.setFilter(new User(filterName.getValue(),filterFirstname.getValue())));
        add(button);

        filter.add(filterFirstname, filterName, button);*/


        filter.add(filterFirstname, filterName);
        filter.setFlexGrow(1,filterFirstname,filterName);
        add(filter);



        userGrid = new Grid<>();

        userGrid.addColumn(User::getId)
                .setHeader("Identifiant")
                .setSortProperty("id");
        userGrid.addColumn(User::getFirstname).setHeader("Prénom")
                .setSortProperty("firstname");
        userGrid.addColumn(User::getName).setHeader("Nom")
                .setSortProperty("name");


        userGrid.addComponentColumn( this::generateEditButton).setHeader(VaadinIcon.COG.create());
        add(userGrid);
        setFlexGrow(1,userGrid);

        userGrid.setDataProvider(filterDataProvider);
        setSizeFull();
    }

    private Component generateEditButton(User user){
        Button button = new Button("Editer", VaadinIcon.EDIT.create());
        button.addClickListener(e -> UI.getCurrent().navigate(UserEditorView.class,user.getId()));
        button.getElement().getThemeList().add("primary");
        button.getElement().getThemeList().add("small");
        return button;
    }

    @Override
    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
        //   userGrid.setItems(userService.findAll());
    }
}