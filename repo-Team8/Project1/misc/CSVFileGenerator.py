import random
import sys

def writeCandidateNames(fileWriter, numOfCandidates):
    candNames = [chr(ord('A') + i) for i in range(numOfCandidates)]
    for i in range(len(candNames)-1):
        fileWriter.write(str(candNames[i]))
        fileWriter.write(",")
    fileWriter.write(str(candNames[len(candNames)-1]))
    fileWriter.write("\n")

def stv(numOfCandidates, numOfVotes, inputFileName):
    fileWriter = open(inputFileName, "w")
    writeCandidateNames(fileWriter, numOfCandidates)
    for i in range(numOfVotes):
        votes = ""
        ls = [i+1 for i in range(numOfCandidates)]
        for j in range(numOfCandidates):
            votes += str(ls.pop(random.randint(0, len(ls) - 1))) + ","
        votes = votes[:-1]
        votes += "\n"
        fileWriter.write(votes)

def stvrandom(numOfCandidates, numOfVotes, inputFileName):
    fileWriter = open(inputFileName, "w")
    writeCandidateNames(fileWriter, numOfCandidates)
    for i in range(numOfVotes):
        votes = ""
        randomNumber = random.randint(0, numOfCandidates - 1)
        ls = [i + 1 for i in range(numOfCandidates-randomNumber)]
        for k in range(randomNumber):
            ls.append("")
        random.shuffle(ls)
        for j in ls:
            votes += str(j) + ","
        votes = votes[:-1]
        votes += "\n"
        fileWriter.write(votes)

def plurality(numOfCandidates, numOfVotes, inputFileName):
    fileWriter = open(inputFileName, "w")
    writeCandidateNames(fileWriter, numOfCandidates)
    for i in range(numOfVotes):
        votePlace = random.randint(1, numOfCandidates)
        votes = ""
        votes += (votePlace-1)*","
        votes += "1,"
        votes += (numOfCandidates-votePlace) * ","
        votes = votes[:-1]
        votes += "\n"
        fileWriter.write(votes)

def pluralityTie(numOfCandidates, numOfVotes, inputFileName):
    fileWriter = open(inputFileName, "w")
    writeCandidateNames(fileWriter, numOfCandidates)
    votePlace = random.randint(1, numOfCandidates)
    votePlace2 = votePlace
    while (votePlace2 == votePlace):
        votePlace2 = random.randint(1, numOfCandidates)
    for i in range(numOfVotes//3):
        votes = ""
        votes += (votePlace-1)*","
        votes += "1,"
        votes += (numOfCandidates-votePlace) * ","
        votes = votes[:-1]
        votes += "\n"
        fileWriter.write(votes)
    for i in range(numOfVotes // 3):
        votes = ""
        votes += (votePlace2 - 1) * ","
        votes += "1,"
        votes += (numOfCandidates - votePlace2) * ","
        votes = votes[:-1]
        votes += "\n"
        fileWriter.write(votes)
    for i in range((numOfVotes // 3)-1):
        votePlace3 = votePlace
        while (votePlace3 == votePlace or votePlace3 == votePlace2):
            votePlace3 = random.randint(1, numOfCandidates)
        votes = ""
        votes += (votePlace3 - 1) * ","
        votes += "1,"
        votes += (numOfCandidates - votePlace3) * ","
        votes = votes[:-1]
        votes += "\n"
        fileWriter.write(votes)


def main():
    if int(sys.argv[1]) == 0:
        stv(int(sys.argv[2]), int(sys.argv[3]), sys.argv[4])
    elif int(sys.argv[1]) == 1:
        plurality(int(sys.argv[2]), int(sys.argv[3]), sys.argv[4])
    elif int(sys.argv[1]) == 2:
        stvrandom(int(sys.argv[2]), int(sys.argv[3]), sys.argv[4])
    else:
        pluralityTie(int(sys.argv[2]), int(sys.argv[3]), sys.argv[4])

main()