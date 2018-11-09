package lu.lusis.demo.ui.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lu.lusis.demo.backend.data.User;
import lu.lusis.demo.backend.repository.UserRepository;
import lu.lusis.demo.ui.MainAppLayout;
import lu.lusis.demo.ui.dataprovider.ExampleFilterDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

/**
 * Liste d'utilisateurs
 * Cette classe illustre comment créer par code une page/vue composée:
 * - d'un filtre
 * - d'une grille affichant le résultat
 *
 */
@PageTitle("Liste d'utilisateurs")
@Route(value = "users",layout = MainAppLayout.class)
public class UserListView extends VerticalLayout {


    private Logger logger = LoggerFactory.getLogger(UserListView.class);

    /** Implementation du Pattern Repository pour exécuter des requêtes filtrées et triées en BDD **/
    private final UserRepository userRepository;

    /** Composants d'interface du filtre **/
    private HorizontalLayout filter;
    private TextField filterFirstname;
    private TextField filterName;

    private TextField filterRegisterNumber;


    /** Grille de présentation des objets User **/
    private Grid<User> userGrid;

    /** Data provider pour la grille **/
    private ExampleFilterDataProvider<User, Integer> filterDataProvider;

    /**
     * Constructeur
     *
     * @param userRepository repository injecté
     *
     */
    public UserListView(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Construction de la page
     */
    @PostConstruct
    public void init() {
        logger.info("init()");

        filterDataProvider = new ExampleFilterDataProvider<>(userRepository);

        initFilter();

        initGrid();

        // layout
        // On ajoute le filtre et la grille
        add(filter);
        add(userGrid);
        setSizeFull();

    }


    /**
     * Initialisation du filtre
     * Le filtre doit contenir 2 champs
     * - Nom (valide si au moins 3 caractères ou vide)
     * - Prénom (valide si au moins 3 caractères ou vide)
     *
     *
     *  Le filtre s'effectue dès qu'on modifie le filtre.
     *  Si le filtre n'est pas valide il ne fait rien
     *
     */
    private void initFilter() {
        // Construction de la layout
        // 2 champs qui prennent toute la largeur
        filter = new HorizontalLayout();
        filter.setWidth("100%");
        filter.setAlignItems(Alignment.START);

        filterFirstname = new TextField("Prénom");
        filterName = new TextField("Nom");
        filterRegisterNumber= new TextField("Matricule");
        filter.add(filterFirstname, filterName,filterRegisterNumber);
        filter.setFlexGrow(1,filterFirstname,filterName);
        // Layout finie


        // Dans Vaadin il y une notion de Binder
        // Il permet de lier le "champ" TextField", DatePicker ... et la propriété JAVA de l'objet
        Binder<User> binder = new Binder<>(User.class);
        // On binde le 1er champ
        binder.forField(filterFirstname) // Champ Vaadin Prénom
                // On valide le champ s'il est vide ou plus de 2 caractères
                .withValidator(firstname -> firstname.isEmpty()|| firstname.length() > 2,"Veuillez saisir au moins 3 caractères")
                // En lecture on utilise la fonction getFirstname, en écriture setFirstname
                .bind(User::getFirstname,User::setFirstname);
        // On binde le 2eme champ
        binder.forField(filterName)
                .withValidator(name -> name.isEmpty()|| name.length() > 2,"Veuillez saisir au moins 3 caractères")
                .bind(User::getName,User::setName);


        binder.forField(filterRegisterNumber)
                .bind(User::getRegisterNumber,User::setRegisterNumber);

        // Dès que l'utilisateur tape un caractère dans le champ
        // le champ est mis à jour -> "value change"
        filterFirstname.setValueChangeMode(ValueChangeMode.EAGER);
        filterName.setValueChangeMode(ValueChangeMode.EAGER);
        filterRegisterNumber.setValueChangeMode(ValueChangeMode.EAGER);


        // Le filtre est stocké dans un objet User
        // On l'initialise à vide
        User filterUser = new User();
        binder.setBean(filterUser);

        // Dès que le binder est mis à jour
        // on filtre si le formulaire est valide
        binder.addValueChangeListener(
                event ->  {
                    // Si binder valide alors on met à jour le filtre
                    if (binder.validate().isOk()) {filterDataProvider.setFilter(filterUser);}
                });

    }

    /**
     * Cette fonction initialise la grille
     * La grille contient une liste d'utilisateurs avec 5 colonnes
     * - Identifiant
     * - Prénom
     * - Nom
     * - Date de naissance
     * - Un bouton éditer qui permet d'accéder à l'écran d'édition
     * - Le header de la colonne éditer contient un bouton ajouter
     */
    private void initGrid() {

        // Création de la grille "vide"
        userGrid = new Grid<>();

        // ajout des colonnes
        // identifiant
        userGrid.addColumn(User::getId)
                .setHeader("Identifiant")
                .setSortProperty("id");
        // Prénom

        userGrid.addColumn(User::getFirstname).setHeader("Prénom")
                .setSortProperty("firstname");
        // Nom
        userGrid.addColumn(User::getName).setHeader("Nom")
                .setSortProperty("name");

        userGrid.addColumn(new LocalDateRenderer<>(User::getBirthdate, "dd/MM/yyyy")).setHeader("Date de naissance")
                .setSortProperty("birthDate");

        userGrid.addColumn(User::getRegisterNumber).setHeader("Matricule");

        // Bouton Editer avec en header le bouton Ajouter
        userGrid.addComponentColumn( this::generateEditButton).setHeader(generateAddUserButton());

        // Ici on utilise un dataprovider Lazy Spring
        // Il va créer la requête en fonction du filtre et du tri
        // on peut utiliser la fonction userGrid.setItems pour un chargement complet
        userGrid.setDataProvider(filterDataProvider);

    }

    /**
     * Génére un bouton qui navigue vers l'écran d'édition
     * avec comme paramètre l'id de l'utilisateur
     *
     * @param user utilisateur à éditer
     * @return bouton d'édition
     */
    private Button generateEditButton(User user){
        Button button = new Button("Editer", VaadinIcon.EDIT.create());
        button.addClickListener(e -> UI.getCurrent().navigate(UserEditorView.class,user.getId()));
        button.getElement().getThemeList().add("primary");
        button.getElement().getThemeList().add("small");
        return button;
    }

    /**
     * Génére un bouton qui navigue vers l'écran d'édition
     * sans paramètre
     *
     * @ Bouton de création
     */
    private Button generateAddUserButton(){
        Button button = new Button("Nouvel utilisateur", VaadinIcon.PLUS.create());
        button.addClickListener(e -> UI.getCurrent().navigate(UserEditorView.class));
        button.getElement().getThemeList().add("primary");
        button.getElement().getThemeList().add("small");
        return button;
    }
}