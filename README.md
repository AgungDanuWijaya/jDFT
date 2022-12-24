# jDFT : JAVA Density Functional Theory For Solid 
jDFT is a Java program to solve the KSDFT equation using the pseudopotential method to get a solid's band structure and total energy.
## Features
- SCF with electron density mixing
- Band structure calculations
- LDA-PZ functionals
## Requirements
- Linux OS
- Java (https://www.java.com/en/download/) version >= 11

## Installation
You can download the package zip file (using Github) or clone this repository to your computer. You can freely extract it into the folder you like.

## Input File Descriptions
-   smar[1,0] : Gaussian smearing for metals (set 1 for smearing)

-   random [1,0] : Initial wave function initiation using random number (set 0 for random )

-   usp [1,0] : The pseudopotential used is the type of ultrasoft
    psudopotential (set 1 for ultrasoft psudopotential)

-   celldm : Crystallographic constants

-   ecutefc : Kinetic energy cutoff (Ry) for wavefunctions

-   ecutrho : Kinetic energy cutoff (Ry) for charge density and
    potential For norm-conserving pseudopotential

-   ibrav : Bravais-lattice index

-   iband : Number of electronic states (bands) to be calculated.

-   num\_atom : Number of types of atoms in the unit cell

-   nat : Number of atoms in the unit cell

-   mix : Mixing factor for self-consistency

-   pos : Atomic positions are in cartesian coordinates, in units of the
    lattice parameter (either celldm(1) or A).

-   k\_point : Read k-points in cartesian coordinates.
## Example calculations

Total energy
------------

    {
        "status": "scf",
        "smar": 0,
        "random": 0,
        "usp": 0.0,
        "celldm": [10.2, 0.0, 0.0, 0.0],
        "ecutwfc": 18.0,
        "ecutrho": 72.0,
        "ibrav": 0,
        "iband": 6,
        "num_atom": 1,
        "nat": 3,
        "mix": 0.7,
        "atom": ["Si"],
        "upf_url": ["/home/agung/Documents/kkk/q-e-qe-6.6/pseudo/Si.pz-vbc.UPF"],
        "lattice": [
            [10.2, 0.0, 0.0],
            [0.0, 10.2, 0.0],
            [0.0, 0.0, 10.2]
        ],
        "degauss_": 0.02,
        "pos": [
            [0.0, 0.0, 0.0],
            [0.25, 0.25, 0.25],
            [0.3, 0.3, 0.3]
        ],
        "atom_pos": [0, 0, 0],
        "weig": [2.0],
        "k_point": [
            [0.0, 0.0, 0.0]
        ]
    }


To run jDFT total energy calculation, execute this command on terminal. You have to run this command inside the jDFT folder. The results of the calculations will be stored in the out.dat file.

    java -jar "url_jdft_folder/dist/jDFT.jar" url_input_file/input.dat > out.dat
     

Band Structure
--------------

You must first perform a total energy calculation to perform a structural band calculation.

    {
        "status": "nscf",
        "smar": 0,
        "random": 0,
        "usp": 0.0,
        "celldm": [10.2, 0.0, 0.0, 0.0],
        "ecutwfc": 18.0,
        "ecutrho": 72.0,
        "ibrav": 0,
        "iband": 6,
        "num_atom": 1,
        "nat": 3,
        "mix": 0.7,
        "atom": ["Si"],
        "upf_url": ["/home/agung/Documents/kkk/q-e-qe-6.6/pseudo/Si.pz-vbc.UPF"],
        "lattice": [
            [10.2, 0.0, 0.0],
            [0.0, 10.2, 0.0],
            [0.0, 0.0, 10.2]
        ],
        "degauss_": 0.02,
        "pos": [
            [0.0, 0.0, 0.0],
            [0.25, 0.25, 0.25],
            [0.3, 0.3, 0.3]
        ],
        "atom_pos": [0, 0, 0],
        "weig": [0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407, 0.07407407407407407],
        "k_point": [
            [-1.0, 0.5, 0.0],
            [-0.9667, 0.4833, 0.0],
            [-0.9333, 0.4667, 0.0],
            [-0.9, 0.45, 0.0],
            [-0.8667, 0.4333, 0.0],
            [-0.8333, 0.4167, 0.0],
            [-0.8, 0.4, 0.0],
            [-0.7667, 0.3833, 0.0],
            [-0.7333, 0.3667, 0.0],
            [-0.7, 0.35, 0.0],
            [-0.6667, 0.3333, 0.0],
            [-0.6333, 0.3167, 0.0],
            [-0.6, 0.3, 0.0],
            [-0.5667, 0.2833, 0.0],
            [-0.5333, 0.2667, 0.0],
            [-0.5, 0.25, 0.0],
            [-0.4667, 0.2333, 0.0],
            [-0.4333, 0.2167, 0.0],
            [-0.4, 0.2, 0.0],
            [-0.3667, 0.1833, 0.0],
            [-0.3333, 0.1667, 0.0],
            [-0.3, 0.15, 0.0],
            [-0.2667, 0.1333, 0.0],
            [-0.2333, 0.1167, 0.0],
            [-0.2, 0.1, 0.0],
            [-0.1667, 0.0833, 0.0],
            [-0.1333, 0.0667, 0.0]
        ]
    }

To run jDFT band structure calculation, execute this command on terminal. You have to run this command inside the jDFT folder.

    java -jar "url_jdft_folder/dist/jDFT.jar" url_input_file/input.dat > out.dat
     
## Example of Cu's band structure calculations
<img src="https://github.com/AgungDanuWijaya/jDFT/blob/master/cu_fix.png" alt="dftk logo" height="400px" />
