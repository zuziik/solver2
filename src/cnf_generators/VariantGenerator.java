package cnf_generators;

/**
 * Trieda reprezentuje rodicovsku triedu pre generatory vsetkych variant sudoku
 */
public abstract class VariantGenerator extends Generator {
    Generator wrapped;

    public VariantGenerator( Generator wrapped ) {
        this.wrapped = wrapped;
    }

    /** Funkcia vygeneruje CNF zabalenej varianty a prida svoje podmienky */
    public void generateCNF(){
        wrapped.generateCNF();
        this.formulas.addAll(wrapped.getCNFFormulas());
    }

}
