/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IOROperationCode
/*     */ {
/*  35 */   static final int LP_ENTER_PROBLEM = 10101;
/*  36 */   static final int LP_ENTER_START = 10102;
/*     */   
/*  38 */   static final int LP_SETUP_PANEL = 10200;
/*  39 */   static final int LP_SETUP_ADD_SLACK_VARIABLE = 10201;
/*  40 */   static final int LP_SETUP_SUBSTRACT_SURPLUS_VARIABLE = 10202;
/*  41 */   static final int LP_SETUP_ADD_ARTIFICIAL_VARIABLE = 10203;
/*  42 */   static final int LP_SETUP_MULTIPLY_EQUATION_BY_MINUS_ONE = 10204;
/*  43 */   static final int LP_SETUP_CONVERT_ONE_VARIABLE_TO_DIFFERENCE_OF_TWO_VARIABLES = 10205;
/*  44 */   static final int LP_SETUP_CONVERT_ONE_VARIABLE_TO_VARIABLE_PLUS_CONSTANT = 10206;
/*  45 */   static final int LP_SETUP_CARRY_CONSTANT_TO_THE_RIGHT_HAND_SIDE = 10207;
/*  46 */   static final int LP_SETUP_CONVERT_TO_ORIGINAL_MODEL = 10208;
/*  47 */   static final int LP_SETUP_SELECT_BIG_M_OR_TWO_PHASE = 10209;
/*  48 */   static final int LP_SETUP_CONTINUE_TWO_PHASE = 10210;
/*  49 */   static final int LP_SETUP_START = 10211;
/*  50 */   static final int LP_SETUP_FINISH = 10212;
/*     */   
/*  52 */   static final int LP_SEL_EQ_AND_ENTER_MULIPLE = 10213;
/*     */   
/*     */ 
/*  55 */   static final int LP_IP_INITIAL_POINT = 10301;
/*  56 */   static final int LP_IP_DO_ITERATION = 10302;
/*  57 */   static final int LP_IP_SET_ALPHA_VALUE = 10303;
/*     */   
/*     */ 
/*  60 */   static final int LP_CHOOSE_ENTER_LEAVE_BASIC = 10401;
/*  61 */   static final int LP_DO_EQUATION_ELIMINATION = 10402;
/*  62 */   static final int LP_ADD_ANOTHER_ARTIFICIAL = 10403;
/*  63 */   static final int LP_ORIGINAL_RAPLACE_CURRENT_OBJECTIVE = 10404;
/*  64 */   static final int LP_DO_WHOLE_ELIMINATION = 10405;
/*  65 */   static final int LP_DO_REMOVE_ARTIFICIAL = 10406;
/*  66 */   static final int LP_SOLVE_START = 10407;
/*  67 */   static final int LP_SET_SIMPLEX_PROCEDURE = 10408;
/*  68 */   static final int LP_SET_MODIFIED_PROCEDURE = 10409;
/*     */   
/*     */ 
/*  71 */   static final int LP_SEL_CHANGE_COEF = 10501;
/*  72 */   static final int LP_SEL_EFFECTION_LOCATION = 10502;
/*  73 */   static final int LP_SEL_ROW_VECTOR = 10503;
/*  74 */   static final int LP_SEL_MATRIX = 10504;
/*  75 */   static final int LP_SEL_COL_VECTOR = 10505;
/*  76 */   static final int LP_SEL_CONSTANT = 10506;
/*  77 */   static final int LP_DO_VALUE_CALC = 10507;
/*  78 */   static final int LP_DO_VECTOR_CALC = 10508;
/*  79 */   static final int LP_SENS_REVERT_ORG = 10509;
/*  80 */   static final int LP_SENS_PROPER_FORM = 105010;
/*  81 */   static final int LP_SENS_DO_SIMPLEX = 10511;
/*  82 */   static final int LP_SENS_DO_DUAL_SIMPLEX = 10512;
/*  83 */   static final int LP_AUTO_SOLVER = 10523;
/*     */   
/*     */ 
/*  86 */   static final int TP_ENTER_PROBLEM = 20101;
/*  87 */   static final int TP_SET_OPTION = 20102;
/*     */   
/*  89 */   static final int TP_START_FIND_FEASIBLE = 20200;
/*  90 */   static final int TP_FIND_FEASIBLE_SET_BASIC = 20201;
/*  91 */   static final int TP_FIND_FEASIBLE_DELETE_ROW = 20202;
/*  92 */   static final int TP_FIND_FEASIBLE_DELETE_COLUMN = 20203;
/*  93 */   static final int TP_FIND_FEASIBLE_CHECK_SOLUTION = 20204;
/*     */   
/*  95 */   static final int TP_START_SOLVE_PROBLEM = 20300;
/*  96 */   static final int TP_START_AUTO_SOLVE_PROBLEM = 20310;
/*     */   
/*  98 */   static final int TP_SOLVE_SET_INITIAL_UV = 20301;
/*     */   
/* 100 */   static final int TP_SOLVE_SET_ENTERING_BASIC = 20302;
/* 101 */   static final int TP_SOLVE_ADD_LOOP_NODE = 20303;
/* 102 */   static final int TP_SOLVE_SET_LEAVING_BASIC = 20304;
/*     */   
/*     */ 
/* 105 */   static final int NA_SELECT_PROBLEM = 30110;
/* 106 */   static final int NA_SELECT_INITIAL_BASIC_ARCS = 30101;
/* 107 */   static final int NA_SELECT_INITIAL_REVERSE_ARCS = 30102;
/* 108 */   static final int NA_ENTER_INITIAL_BASIC_FLOW = 30103;
/* 109 */   static final int NA_FORM_A_CYCLE = 30104;
/* 110 */   static final int NA_SELECT_ENTER_BASIC = 30105;
/* 111 */   static final int NA_SELECT_LEAVE_BASIC = 30106;
/* 112 */   static final int NA_LEAVE_BASIC_NEED_REVERSE = 30107;
/* 113 */   static final int NA_COMPUTE_ALL_DELTA = 30108;
/* 114 */   static final int NA_ENTER_FLOW_OF_REVERSED_ARC = 30109;
/* 115 */   static final int NA_CALCULATE_Z = 30111;
/*     */   
/*     */ 
/* 118 */   static final int IP_ENTER_PROBLEM = 40101;
/* 119 */   static final int IP_BIP_FIRST_SOLUTION = 40102;
/* 120 */   static final int IP_BIP_BRANCH = 40103;
/* 121 */   static final int IP_BIP_SET_INCUMBENT = 40104;
/* 122 */   static final int IP_BIP_SET_FATHOMED = 40105;
/* 123 */   static final int IP_MIP_FIRST_SOLUTION = 40202;
/* 124 */   static final int IP_MIP_BRANCH = 40203;
/* 125 */   static final int IP_MIP_SET_INCUMBENT = 40204;
/* 126 */   static final int IP_MIP_SET_FATHOMED = 40205;
/* 127 */   static final int IP_MIP_CHECK_FIRST_ITERATION = 40206;
/*     */   
/*     */ 
/* 130 */   static final int NL_SELECT_ALGORITHM = 50103;
/* 131 */   static final int NL_ONED_ENTER_FUNCTION = 50101;
/* 132 */   static final int NL_ONED_DO_ITERATE = 50102;
/*     */   
/* 134 */   static final int NL_GRADIENT_ENTER_FUNCTION = 50201;
/* 135 */   static final int NL_GRADIENT_FORM_T_FUNCTION = 50202;
/* 136 */   static final int NL_GRADIENT_CALCULATE_T = 50203;
/* 137 */   static final int NL_GRADIENT_CALCULATE_X = 50204;
/* 138 */   static final int NL_GRADIENT_NEW_X = 50205;
/* 139 */   static final int NL_AUTOGRADIENT_SOLVE_PROBLEM = 50206;
/*     */   
/* 141 */   static final int NL_FRANKWOLF_ENTER_OBJECTIVE = 50401;
/* 142 */   static final int NL_FRANKWOLF_ENTER_DERIVATIVE = 50402;
/* 143 */   static final int NL_FRANKWOLF_LP_OBJECTIVE = 50403;
/* 144 */   static final int NL_FRANKWOLF_LP_DO_SIMPLEX = 50404;
/* 145 */   static final int NL_FRANKWOLF_FORM_X_T_FUNCTION = 50405;
/* 146 */   static final int NL_FRANKWOLF_FORM_T_FUNCTION = 50406;
/* 147 */   static final int NL_FRANKWOLF_CALCULATE_T_AND_X = 50407;
/*     */   
/* 149 */   static final int NL_SUMT_ENTER_NUMBER = 50501;
/* 150 */   static final int NL_SUMT_ENTER_FUNCTION = 50502;
/* 151 */   static final int NL_SUMT_NEW_ITERATION = 50503;
/* 152 */   static final int NL_SUMT_DO_CALCULATE = 50504;
/*     */   
/*     */ 
/*     */ 
/* 156 */   static final int NL_ANNEALING_ENTER_OBJECTIVE = 50601;
/* 157 */   static final int NL_GENETIC_ENTER_OBJECTIVE = 50701;
/* 158 */   static final int NL_GENETIC_COMPUTE = 50702;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 163 */   static final int MC_ENTER_TRANSITION_MATRIX = 10101;
/* 164 */   static final int MC_CHAPMAN_KOLMOGOROV_EQUATIONS = 10102;
/*     */   
/*     */ 
/* 167 */   static final int QT_JACKSONNETWORK_SOLVE_PROBLEM = 20101;
/*     */   
/*     */ 
/* 170 */   static final int IT_EXP_DIS_SINGLE = 30101;
/* 171 */   static final int IT_EXP_DIS_SINGLE_WITH_SETUP = 30102;
/* 172 */   static final int IT_EXP_DIS_TWO = 30103;
/* 173 */   static final int IT_EXP_DIS_INFINITE = 30104;
/*     */   
/* 175 */   static final int IT_UNI_DIS_SINGLE = 30201;
/* 176 */   static final int IT_UNI_DIS_SINGLE_WITH_SETUP = 30202;
/* 177 */   static final int IT_UNI_DIS_TWO = 30203;
/* 178 */   static final int IT_UNI_DIS_INFINITE = 30204;
/*     */   
/*     */ 
/* 181 */   static final int MKD_SELECT_PROCEDURE = 40105;
/* 182 */   static final int MKD_ENTER_NUMBER = 40101;
/* 183 */   static final int MKD_ENTER_COST_MATRIX = 40102;
/* 184 */   static final int MKD_ENTER_TRANSITION_MATRIX = 40103;
/* 185 */   static final int MKD_ENTER_INITIAL_DECISION = 40104;
/*     */   
/* 187 */   static final int MKD_AVERAGE_SELECT_GR_COEF = 40201;
/* 188 */   static final int MKD_AVERAGE_CALCULATE_GR = 40202;
/* 189 */   static final int MKD_AVERAGE_PREPARE_POLICY_IMPROVE = 40203;
/* 190 */   static final int MKD_AVERAGE_SELECT_EXPRESSIN_COEF = 40204;
/* 191 */   static final int MKD_AVERAGE_CALCULATE_EXPRESSION = 40205;
/* 192 */   static final int MKD_AVERAGE_GET_NEW_DECISION = 40206;
/* 193 */   static final int MKD_AVERAGE_ITERATION_END = 40207;
/*     */   
/* 195 */   static final int MKD_DISCOUNT_SELECT_GR_COEF = 40301;
/* 196 */   static final int MKD_DISCOUNT_CALCULATE_GR = 40302;
/* 197 */   static final int MKD_DISCOUNT_PREPARE_POLICY_IMPROVE = 40303;
/* 198 */   static final int MKD_DISCOUNT_SELECT_EXPRESSIN_COEF = 40304;
/* 199 */   static final int MKD_DISCOUNT_CALCULATE_EXPRESSION = 40305;
/* 200 */   static final int MKD_DISCOUNT_GET_NEW_DECISION = 40306;
/* 201 */   static final int MKD_DISCOUNT_ITERATION_END = 40307;
/*     */   
/* 203 */   static final int MKD_SUCCESSIVE_ENTER_INITIAL = 40401;
/* 204 */   static final int MKD_SUCCESSIVE_SELECT_V_COEF = 40402;
/* 205 */   static final int MKD_SUCCESSIVE_CALCULATE_EXPRESSION = 40403;
/* 206 */   static final int MKD_SUCCESSIVE_GET_NEW_DICISION = 40404;
/* 207 */   static final int MKD_SUCCESSIVE_ITERATION_END = 40405;
/*     */   
/*     */ 
/* 210 */   static final int SA_ENTER_PROBLEM = 50101;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 216 */   static final int SA_GET_RANDOM_TIME = 50201;
/*     */   
/* 218 */   static final int SA_FINISH_GETTING_RANDOM_TIME = 50202;
/*     */   
/*     */ 
/* 221 */   static final int SA_SELECT_EVENT = 50203;
/*     */   
/* 223 */   static final int SA_SELECT_CUSTOMER_ACTION = 50204;
/*     */   
/* 225 */   static final int SA_SELECT_SERVER_ACTION = 50205;
/*     */   
/*     */ 
/* 228 */   static final int SA_START_SIMULATION = 50301;
/* 229 */   static final int SA_FINISH_SIMULATION = 50302;
/*     */   
/*     */ 
/*     */ 
/* 233 */   static final int TSP_SOLVE_SET_INITIAL_UV = 60001;
/* 234 */   static final int TSP_FIND_FEASIBLE_DELETE_ROW = 60002;
/* 235 */   static final int TSP_FIND_FEASIBLE_DELETE_COLUMN = 60003;
/* 236 */   static final int TSP_FIND_FEASIBLE_SET_BASIC = 60004;
/* 237 */   static final int TSP_SOLVE_SET_LEAVING_BASIC = 60005;
/* 238 */   static final int TSP_SOLVE_ADD_LOOP_NODE = 60006;
/* 239 */   static final int TSP_SOLVE_SET_ENTERING_BASIC = 60007;
/*     */   
/*     */ 
/* 242 */   static final int TSP_ENTER_PROBLEM = 60008;
/*     */   
/*     */ 
/* 245 */   static final int TSP_SET_OPTION = 60012;
/* 246 */   static final int TSP_START_TABU_SEARCH = 60009;
/* 247 */   static final int TSP_FINISH_TABU_SEARCH = 60013;
/*     */   
/* 249 */   static final int TSP_START_SIMULSTED_ANNEALING = 60010;
/* 250 */   static final int TSP_FINISH_SIMULSTED_ANNEALING = 60011;
/*     */   
/* 252 */   public static final int TSP_START_GENETIC_ANNEALING = 60014;
/* 253 */   public static final int TSP_FINISH_GENETIC_ANNEALING = 60015;
/*     */ }


/* Location:              C:\Program Files (x86)\Accelet\IORTutorial\IORTutorial.jar!\IOROperationCode.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */