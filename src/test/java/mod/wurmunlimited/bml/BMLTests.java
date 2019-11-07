package mod.wurmunlimited.bml;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.creatures.TradeHandler;
import com.wurmonline.server.economy.Change;
import com.wurmonline.server.economy.Economy;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.questions.FriendQuestion;
import com.wurmonline.server.questions.MultiPriceManageQuestion;
import com.wurmonline.server.questions.Question;
import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.wurmonline.server.questions.Question.itemNameWithColorByRarity;
import static org.junit.jupiter.api.Assertions.*;

class BMLTests {
    private final String startBML = "border{center{text{type=\"bold\";text=\"\"}};null;scroll{vertical=\"true\";horizontal=\"false\";varray{rescale=\"true\";passthrough{id=\"id\";text=\"0\"}";
    private final String endBML = "}};null;null;}";

    private class BMLTop extends BML {
        @Override
        StringBuilder buildBML() {
            return new StringBuilder();
        }
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        ReflectionUtil.setPrivateField(null, Question.class.getDeclaredField("ids"), 0);
    }

    private String createBMLString() {
        return new BMLBuilder(0)
                       .text("")
                       .checkbox("checkbox")
                       .checkbox("checkbox2", "labelled")
                       .entry("a_number", "23", 2)
                       .harray(b -> b.button("Send"))
                       .build();
    }

    @Test
    void testBalancedCurlyBraces() {
        String bml = createBMLString();
        assertEquals(
                bml.chars().filter(c -> c == '{').count(),
                bml.chars().filter(c -> c == '}').count());
    }

    @Test
    void testNoSemiColonsAfterLastTagAttribute() {
        String bml = createBMLString();
        bml = bml.substring(0, bml.length() - 2);
        assertFalse(bml.contains(";}"));
    }

    @Test
    void testBorderContainsFiveEntries() {
        String bml = createBMLString();
        Pattern pattern = Pattern.compile("(};)|(null;)");
        Matcher matcher = pattern.matcher(bml);
        System.out.println(bml);
        int count = 0;
        while (matcher.find())
            ++count;
        assertEquals(5, count);
    }

    private String createFriendQuestionBml(Creature subject) {
        return new BMLBuilder(0)
                       .text(subject.getName() + " wants to add you to " + subject.getHisHerItsString() + " friends list.")
                       .text("This will mean " + subject.getHeSheItString() + " will see you log on and off, and be able to allow you into structures " + subject.getHeSheItString() + " controls.")
                       .text("Do you accept?")
                       .radio("join", "accept", "Accept")
                       .radio("join", "decline", "Decline", true)
                       .harray(b -> b.button("Send"))
                       .build();
    }

    // Changed to harray.
    @Test
    void testButtonSetAddsSpacersBetweenButtons() {
        String bml = new BMLBuilder(0)
                             .harray(b -> b.button("1", "1").spacer().button("1", "1"))
                             .build();
        assertEquals(
                startBML +
                        "harray{button{text=\"1\";id=\"1\"};label{text=\" \";id=\"spacedlxg\"};button{text=\"1\";id=\"1\"}}" +
                        endBML,
                bml);
    }

    @Test
    void testButtonSetDoesNotFinishWithSpacer() {
        String bml = new BMLBuilder(0)
                             .harray(b -> b.button("1", "1"))
                             .build();
        assertTrue(bml.endsWith("button{text=\"1\";id=\"1\"}}" + endBML));
    }

    private String createMultiQuestionBml(Creature trader) {
        Item[] items = trader.getInventory().getAllItems(false);
        Arrays.sort(items);
        DecimalFormat df = new DecimalFormat("#.##");
        AtomicInteger x = new AtomicInteger(0);
        return new BMLBuilder(0)
                .text(trader.getName() + " may put up " + (TradeHandler.getMaxNumPersonalItems() - trader.getNumberOfShopItems()) + " more items for sale.")
                .text("Prices for " + trader.getName()).bold().text("")
                .table(new String[] {"Item name", "QL", "DMG", "Gold", "Silver", "Copper", "Iron"},
                        Arrays.asList(items), (item, b) -> {
                            Change change = Economy.getEconomy().getChangeFor(item.getPrice());
                            x.incrementAndGet();
                            return b.raw(itemNameWithColorByRarity(item))
                                .label(df.format(item.getQualityLevel()))
                                .label(df.format(item.getDamage()))
                                .harray(r -> r.entry(x.get() + "g", String.valueOf(change.getGoldCoins()), 3).label(" "))
                                .harray(r -> r.entry(x.get() + "s", String.valueOf(change.getSilverCoins()), 2).label(" "))
                                .harray(r -> r.entry(x.get() + "c", String.valueOf(change.getCopperCoins()), 2).label(" "))
                                .harray(r -> r.entry(x.get() + "i", String.valueOf(change.getIronCoins()), 2).label(" "));
                        })
                .harray(b -> b.button("Send"))
                .build();
    }

    @Test
    void testHArraySeparatesChildren() {
        String bml = new BMLTop()
                .harray(b -> b.text("test").entry("e", 1).label("test2"))
                .build().replace("}};null;null;}", "");

        assertEquals("harray{text{text=\"test\"};input{text=\"\";id=\"e\";maxchars=\"1\"};label{text=\"test2\"}}", bml);
    }

    @Test
    void testTableSeparatesChildren() {
        String bml = new BMLTop()
                             .table(new String[] {"1", "2"}, Arrays.asList("a", "b", "c"),
                                     (item, b) -> b.label(item).label(item))
                             .build().replace("}};null;null;}", "");

        assertEquals("table{rows=\"4\";cols=\"2\";label{text=\"1\"};label{text=\"2\"}label{text=\"a\"};label{text=\"a\"}label{text=\"b\"};label{text=\"b\"}label{text=\"c\"};label{text=\"c\"}}", bml);
    }
}

