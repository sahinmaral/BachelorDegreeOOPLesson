package entities;

import java.util.ArrayList;
import java.util.List;

public class Cv {
    public int id;
    public String githubAdress;
    public String linkedinAdress;
    public String coveringLetter;
    public CvImage cvImage;
    public List<ProgrammingTechnology> programmingTechnologies = new ArrayList<>();
    public List<ForeignLanguage> foreignLanguages = new ArrayList<>();
    public Candidate candidate;
    public List<School> schools = new ArrayList<>();
}
