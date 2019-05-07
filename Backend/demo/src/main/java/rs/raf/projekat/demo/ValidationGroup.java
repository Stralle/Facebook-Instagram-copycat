package rs.raf.projekat.demo;

import javax.validation.groups.Default;

public class ValidationGroup {

    public ValidationGroup() {}

    public interface Save extends Default {};

    public interface Update extends Default {};

    public interface Login extends Default {};

}
