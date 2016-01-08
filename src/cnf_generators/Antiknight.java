package cnf_generators;

/**
 * Trieda reprezentuje Antiknight sudoku: Ziadne dve policka, ktore obsahuju rovnake cisla, sa nesmu od seba
 * nachadzat na jeden skok sachoveho kona.
 */
public class Antiknight extends VariantGenerator {
    public Antiknight(Generator wrapped){
        super(wrapped);
    }

    /** Funkcia prida podmienku AK sudoku: Ziadne dve policka, ktore obsahuju rovnake cisla, sa nesmu od seba
     * nachadzat na jeden skok sachoveho kona.*/
    private void generateAK(){
        //TODO
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    @Override
    public void generateCNF(){
        super.generateCNF();
        generateAK();
    }
}
