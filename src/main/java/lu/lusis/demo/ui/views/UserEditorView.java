package lu.lusis.demo.ui.views;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;
import lu.lusis.demo.backend.data.User;
import lu.lusis.demo.backend.repository.UserRepository;
import lu.lusis.demo.ui.MainAppLayout;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Ce composant permet de créer ou mettre à jour un utilisateur
 *
 * Elle contient le formulaire d'édition avec 2 boutons:
 * - un bouton sauvegarde
 * - un bouton annuler
 *
 * Cette page a un paramètre Integer qui correspond à l'id
 * Il est optionnel (Création)
 */
@PageTitle("Edition utilisateur")
@Tag("user-edit")
@Route(value = "users/edit",layout = MainAppLayout.class)
public class UserEditorView extends Div implements HasUrlParameter<Integer> {

    private Logger logger = LoggerFactory.getLogger(UserEditorView.class);

    private final UserRepository userRepository;

    private TextField firstname;

    private TextField lastname;

    private TextField registerNumber;


    private DatePicker birthDate;

    private Binder<User> binder;

    private Button save;

    private Button cancel;


    public UserEditorView(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void init() {
        logger.info("init()");

        initLayout();
        initBinding();

    }

    /**
     * On initialise la layout
     * Un formulaire avec
     * Prénom
     * Nom
     * Matricule
     * Date de naissance
     */
    private void initLayout(){

        FormLayout layout = new FormLayout();
        firstname = new TextField("Prénom");
        lastname = new TextField("Nom");
        registerNumber = new TextField("Matricule");
        registerNumber.setValueChangeMode(ValueChangeMode.EAGER);

        birthDate = new DatePicker("Date de naissance");

        layout.add(firstname,lastname,registerNumber, birthDate);

        save = new Button("Sauvegarder");
        save.getElement().setAttribute("theme", "primary");
        cancel = new Button("Annuler");

        cancel.addClickListener(e -> UI.getCurrent().navigate(UserListView.class));
        save.addClickListener(this::saveAction);

        add(layout);
        add (new HorizontalLayout(save,cancel));
    }

    private void initBinding(){

        binder = new Binder<>();
        // Prénom obligatoire
        binder.forField(firstname).asRequired("Vous devez remplir le prénom")
                .bind(User::getFirstname, User::setFirstname);
        // Nom obligatoire
        binder.forField(lastname).asRequired("Vous devez remplir le nom")
                .bind(User::getName, User::setName);

        // Le matricule est obligatoire et doit faire 13 digits
        binder.forField(registerNumber).asRequired("Vous devez remplir le matricule")
                .withValidator(this::checkRegisterNumber,"Le matricule doit être composé de 13 chiffres")
                .bind(User::getRegisterNumber,User::setRegisterNumber);
        // Date de naissance
        binder.bind(birthDate,User::getBirthdate,User::setBirthdate);

    }

    /**
     * sauvegarde l'utilisateur si le formulaire est valide
     * sinon affiche un message d'erreur
     *
     * @param buttonClickEvent
     */
    private void saveAction(ClickEvent<Button> buttonClickEvent) {
        if (binder.validate().isOk()){
            // save
            userRepository.save(binder.getBean());
            UI.getCurrent().navigate(UserListView.class);
            Notification.show("utilisateur sauvegardé");
        } else {
            Notification.show("Veuillez vérifier votre formulaire");
        }
    }

    /**
     * Vérifie si le matricule fait 13 digits
     *
     * @param registerNumber Matricule
     * @return true si valide
     */
    private boolean checkRegisterNumber(String registerNumber) {
        boolean registeredNumberOk;

        if (registerNumber != null && registerNumber.length() != 13){
            registeredNumberOk = false;
        } else {
            registeredNumberOk =  StringUtils.isNumeric(registerNumber);
        }
        return registeredNumberOk;

    }

    /**
     * En arrivant sur l'écran, remplit le formulaire avec l'utilisateur
     * Valide le formulaire directement
     *
     * @param user utilisateur à éditer
     */
    private void edit(User user){
        binder.setBean(user);
        binder.validate().isOk();
    }

    /**
     * Permet de gérer le paramètre passé
     * S'il est nul alors on créé un nouvel utilisateur
     * S'il est non nul alors on va chercher l'utilisateur correspondant
     *   Si trouvé on edite l'utilisateur
     *   sinon on renvoie sur le page d'erreur NotFound
     *
     * @param beforeEvent evenement
     * @param id identifiant de l'utilisateur
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Integer id) {
        // Recherche en base de données
        if (id == null){
            setTitle("Création d'un utilisateur");
            edit(new User());
        } else {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                setTitle("Modification de l'utilisateur "+user.get().getName());
                // L'utilisateur a été trouvé on met à jour le modèle
                edit(user.get());
            } else {
                // Pas d'utilisateur avec cet identifiant
                // On jette une exception NotFoundException
                throw new NotFoundException();
            }
        }
    }
}
