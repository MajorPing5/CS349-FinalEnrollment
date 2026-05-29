package model;

/**
 * All listed majors currently offered within department of focus. In this case,
 * due to previously established Project Simplifications & Assumptions, this list only includes all
 * that is offered for the School of Science and Engineering.
 * 
 * <p> NOTE: Only {@code INFOTECH} should be paired with the {@code B} degree type
 */
public enum MajorDiscipline {
  // Biological and Biomedical Systems
  BIO,
  BMD_ENGR,

  // Computing, Analytics, and Mathematics
  CS,
  IT,
  MATHANDSTATS,

  // Energy, Matter and Systems
  CHEM,
  ECE,
  MEC_ENGR,
  PHYS,
  
  // Natural and Built Environment
  ARCH,
  UPD,
  CIV_ENGR,
  ENV_SCI,
  ENV_STDY
}
