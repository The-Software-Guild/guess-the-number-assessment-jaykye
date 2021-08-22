package GuessTheNumber.dao;

import GuessTheNumber.TestApplicationConfiguration;
import GuessTheNumber.model.Game;
import GuessTheNumber.model.Round;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
class GuessTheNumberRoundDaoDBTest {
    @Autowired
    GuessTheNumberRoundDao roundDao;
    @Autowired
    GuessTheNumberGameDao gameDao;


    @Before
    public void setUp() {
        // 여기서 따로 establish connection 하기 싫은가보다. 그래서 getAllRounds 사용함 (which internally establish connection.)
        // 대신 이 method가 작동을 잘 해야지.
        // connection 만들면 그냥 drop해버리면 되는데. jdbc(DataSource).update("Drop table room;")
        List<Round> rounds = roundDao.getAllRounds();
        for(Round round : rounds) {
            roundDao.deleteRoundById(round.getId());
        }
        List<Game> games = gameDao.getAllGames();
        for(Game game : games) {
            gameDao.deleteGameById(game.getGameId());
        }
    }


    @Test
    public void testAddGetRound() {
        // Adding start from taking care of the primary keys of the foreign keys.
        Game game = new Game();
        game.setGameId(1);
        game.setAnswer("2341");
        game.setFinished(false);
        game = gameDao.addGame(game);

        Round round = new Round();
        round.setGuess("1234");
        round.setTimeOfGuess(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        round.setResult("e:0:p:0");
        round.setNumExactMatch(0);
        round.setNumPartialMatch(0);
        round.setGameId(game.getGameId());

        round = roundDao.addRound(round);
        Round fromDao = roundDao.getRoundById(round.getId());


        System.out.println(round.getId());
        System.out.println(fromDao.getId());
        System.out.println(round.getGuess());
        System.out.println(fromDao.getGuess());
        System.out.println(round.getTimeOfGuess());
        System.out.println(fromDao.getTimeOfGuess());

        System.out.println(round.getNumExactMatch());
        System.out.println(round.getNumPartialMatch());

        System.out.println(fromDao.getNumExactMatch());
        System.out.println(fromDao.getNumPartialMatch());

        System.out.println(game.getGameId());
        System.out.println(fromDao.getGameId());

        assertEquals(round, fromDao);
        // 알았다. 범인은 numExactMatch, numPartialMatch
    }

//    @Test
//    public void testGetAllRounds() {
//        Round room = new Round();
//        room.setName("Test Round");
//        room.setDescription("Test Round Description");
//        roomDao.addRound(room);
//
//        Round room2 = new Round();
//        room2.setName("Test Round 2");
//        room2.setDescription("Test Round 2");
//        roomDao.addRound(room2);
//
//        List<Round> rooms = roomDao.getAllRounds();
//
//        assertEquals(2, rooms.size());
//        assertTrue(rooms.contains(room));
//        assertTrue(rooms.contains(room2));
//    }
//
//    @Test
//    public void testUpdateRound() {
//        Round room = new Round();
//        room.setName("Test Round");
//        room.setDescription("Test Round Description");
//        room = roomDao.addRound(room);
//
//        Round fromDao = roomDao.getRoundById(room.getId());
//
//        assertEquals(room, fromDao);
//
//        room.setName("Another Test Round");
//
//        roomDao.updateRound(room);
//
//        assertNotEquals(room, fromDao);
//
//        fromDao = roomDao.getRoundById(room.getId());
//
//        assertEquals(room, fromDao);
//    }
//
//    @Test
//    public void testDeleteRound() {
//        Round room = new Round();
//        room.setName("Test Round");
//        room.setDescription("Test Round Description");
//        room = roomDao.addRound(room);
//
//        Employee employee = new Employee();
//        employee.setFirstName("Test First");
//        employee.setLastName("Test Last");
//        employee = employeeDao.addEmployee(employee);
//
//        Meeting meeting = new Meeting();
//        meeting.setName("Test Meeting");
//        meeting.setTime(LocalDateTime.now());
//        meeting.setRound(room);
//        List<Employee> employees = new ArrayList<>();
//        employees.add(employee);
//        meeting.setAttendees(employees);
//        meeting = meetingDao.addMeeting(meeting);
//
//        roomDao.deleteRoundById(room.getId());
//
//        Round fromDao = roomDao.getRoundById(room.getId());
//        assertNull(fromDao);
//    }
//
//
}