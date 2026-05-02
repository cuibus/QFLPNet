import fuzzy.*;
import org.jgap.*;
import org.jgap.impl.*;

public class QFLPN_testCasesMain {
    private static final int NUM_EVOLUTIONS = 100;
    public static void main(String[] args) throws
            InvalidConfigurationException{
        double targetAmount = 1.84;
        Configuration conf = new DefaultConfiguration();
        Configuration.resetProperty(Configuration.PROPERTY_FITEVAL_INST);
        conf.setFitnessEvaluator(new DeltaFitnessEvaluator());
        conf.setPreservFittestIndividual(true);
        conf.setKeepPopulationSizeConstant(true);

        FitnessFunction fitnessFunction = new SampleFitnessFunction();
        conf.setFitnessFunction(fitnessFunction);
        int nrGenes = 16+4+16;
        IChromosome sampleChromosome = new Chromosome(conf, new IntegerGene(conf, 1, 4), nrGenes);
        conf.setSampleChromosome(sampleChromosome);
        conf.setPopulationSize(200);
        Genotype population = Genotype.randomInitialGenotype(conf);
        for (int i = 0; i < NUM_EVOLUTIONS; i++) {
            population.evolve();
            IChromosome bestSolutionSoFar = population.getFittestChromosome();
            DisplayIndividual(i, bestSolutionSoFar);
        }
    }
    public static void DisplayIndividual(int generation, IChromosome chr){
        System.out.print(generation + ". Fitness value: " + chr.getFitnessValue() + ", ");
        Table2x1 QFLRS1 = SampleFitnessFunction.mapping2x1(chr,0); //mapping
        Table1x1 QFLRS2 = SampleFitnessFunction.mapping1x1(chr, 16); //mapping
        Table2x1 QFLRS3 = SampleFitnessFunction.mapping2x1(chr, 20); //mapping
        System.out.print("cc values: ");
        for (int i = 0; i< TestCaseQFLPN.scenarios.length; i++){
            System.out.print(SampleFitnessFunction.executeTestCase(i, QFLRS1, QFLRS2, QFLRS3) + " ");
        }
        System.out.print(", ");
        System.out.print("table1: "+ QFLRS1 + ", ");
        System.out.print("table2: "+ QFLRS2 + ", ");
        System.out.print("table3: "+ QFLRS3);
        System.out.println();
    }
}

class SampleFitnessFunction extends FitnessFunction {
    final double POSITIVE_BIAS = 7;
    public double evaluate(IChromosome chr) {
        Table2x1 QFLRS1 = mapping2x1(chr,0); //mapping
        Table1x1 QFLRS2 = mapping1x1(chr, 16); //mapping
        Table2x1 QFLRS3 = mapping2x1(chr, 20); //mapping

        double fitness = 0;
        for (int i = 0; i < TestCaseQFLPN.scenarios.length; i++){
            double cc = executeTestCase(i, QFLRS1, QFLRS2, QFLRS3);
            double cc_desired = TestCaseQFLPN.scenarios[i].cc_desired;

            if (cc_desired <= 2) {
                if (cc <= 2) { fitness -= 1; }
                else if (cc >= 3) { fitness += 1; }
            } else if (cc_desired <= 3) {
                if (2 <= cc && cc <= 3) { fitness -=1; }
            } else {
                if (cc <= 2) { fitness += 1; }
                else if (cc >= 3) { fitness -= 1; }
            }

            fitness = fitness + 0.3*Math.abs(TestCaseQFLPN.scenarios[i].cc_desired - cc);
        }
        return fitness + POSITIVE_BIAS;
    }
    public static double executeTestCase(int testIndex, Table2x1 QFLRS1, Table1x1 QFLRS2, Table2x1 QFLRS3){
        FuzzyToken PP = new FuzzyToken(TestCaseQFLPN.scenarios[testIndex].pp);
        FuzzyToken PL = new FuzzyToken(TestCaseQFLPN.scenarios[testIndex].pl);
        FuzzyToken EB = new FuzzyToken(TestCaseQFLPN.scenarios[testIndex].eb);
        FuzzyToken[] UR1 = QFLRS1.execute(new FuzzyToken[] { EB, PP });
        FuzzyToken[] UR2 = QFLRS2.execute(new FuzzyToken[] { PL });
        FuzzyToken[] CC = QFLRS3.execute(new FuzzyToken[] { UR1[0], UR2[0] });
        double cc = CC[0].defuzzify();
        return cc;
    }

    public static Table2x1 mapping2x1(IChromosome chr, int startPosition) {
        int[][] rules = new int[4][4];
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++){
                rules[i][j] = getValueAtGene(chr, startPosition + i*4+j);
            }
        Table2x1 t = (Table2x1)Table2x1.fromIntVector("", rules);
        return t;
    }
    public static Table1x1 mapping1x1(IChromosome chr, int startPosition) {
        int[] rules = new int[4];
        for (int i = 0; i < 4; i++)
            rules[i] = getValueAtGene(chr, startPosition + i);
        Table1x1 t = (Table1x1)Table1x1.fromIntVector("", rules);
        return t;
    }

    public static int getValueAtGene(IChromosome chr, int position) {
        Integer numCoins = (Integer)chr.getGene(position).getAllele();
        return numCoins.intValue();
    }
}