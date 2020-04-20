package my.org.site;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class MainController {
    private static List<FindInLog> persons = new ArrayList();

//    static {
//        persons.add(new Person("Bill", "Gates"));
//        persons.add(new Person("Steve", "Jobs"));
//    }

    // Инъетировать (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;

    @Value("${error.message}")
    private String errorMessage;

//    @Value("${path.message}")
//    private String path;


    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("message", message);

        return "index";
    }

    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String personList(Model model) {

        model.addAttribute("persons", persons);

        return "personList";
    }

//    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.GET)
//    public String addPersonForm(Model model) {
//
//        PersonForm personForm = new PersonForm();
//        model.addAttribute("personForm", personForm);
//
//
//        return "addPerson";
//    }
//
//    @RequestMapping(value = { "/addPerson" }, method = RequestMethod.POST)
//    public String addPersonSave(Model model, //
//                                @ModelAttribute("personForm") PersonForm personForm) {
//
//        String firstName = personForm.getFirstName();
//        String lastName = personForm.getLastName();
//        System.out.println("----------> "+firstName);
//        System.out.println("----------> "+lastName);
//
//        if (firstName != null && firstName.length() > 0 //
//                && lastName != null && lastName.length() > 0) {
//            Person newPerson = new Person(firstName, lastName);
//           // persons.add(new Person);
//
//            return "redirect:/personList";
//        }
//        String error = "First Name & Last Name is required!";
//        model.addAttribute("errorMessage", error);
//        return "addPerson";
//    }



    @RequestMapping(value = { "/formFindLog" }, method = RequestMethod.GET)
    public String addfindInLog(Model model) {
        FindInLog findInLog = new FindInLog();
        model.addAttribute("findInLog", findInLog);

        return "formFindLog";
    }

    @RequestMapping(value = { "/formFindLog" }, method = RequestMethod.POST)
    public String findInLog(Model model, //
                                @ModelAttribute("findInLog") FindInLog findInLog
    )  {

        String path = findInLog.getPath();
        String name = findInLog.getLogFilter();

        try {
            FileReader  file = new FileReader(path);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String s =scanner.nextLine();
                if(s.contains(name)){
                    persons.add(new FindInLog(name,s));
                    System.out.println(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "redirect:/personList";
    }


}