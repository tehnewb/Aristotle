package versions.ver637.model.player.flags;

import static com.framework.map.RSDirection.*;

import java.util.stream.Stream;

import com.framework.map.RSDirection;
import com.framework.map.path.RSRouteStep;
import com.framework.network.RSFrame;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import versions.ver637.model.UpdateFlag;

@RequiredArgsConstructor
public class MovementFlag implements UpdateFlag {

	@Getter
	private final RSRouteStep firstStep;
	@Getter
	private final RSRouteStep secondStep;

	private boolean showAsRunning;
	private boolean displayRunningFlag;
	private int flagValue;

	public MovementFlag(RSRouteStep firstStep, RSRouteStep secondStep, boolean running) {
		this.firstStep = firstStep;
		this.secondStep = secondStep;
		this.showAsRunning = running;
		RSDirection direction = firstStep.direction();
		if (secondStep != null) {
			direction = RSDirection.combined(firstStep.direction(), secondStep.direction());
			if (direction == None) {
				this.displayRunningFlag = true;
				this.flagValue = Run.of(firstStep.direction(), secondStep.direction()).getFlagValue();
				return;
			}
		}
		this.displayRunningFlag = false;
		this.flagValue = Walk.of(direction).getFlagValue();
	}

	@Override
	public int mask() {
		return 0x1;
	}

	@Override
	public int ordinal() {
		return UpdateFlag.MovementOrdinal;
	}

	@Override
	public void write(RSFrame block) {
		block.writeByteA(showAsRunning ? 2 : 1);
	}

	public boolean isRunning() {
		return displayRunningFlag;
	}

	/**
	 * Returns the movement value for this {@code MovementFlag}.
	 * 
	 * @return the movement value
	 */
	public int getFlagValue() {
		return flagValue;
	}

	@RequiredArgsConstructor
	public enum Walk {
		SouthWest(0, RSDirection.SouthWest),
		South(1, RSDirection.South),
		SouthEast(2, RSDirection.SouthEast),
		West(3, RSDirection.West),
		East(4, RSDirection.East),
		NorthWest(5, RSDirection.NorthWest),
		North(6, RSDirection.North),
		NorthEast(7, RSDirection.NorthEast);

		@Getter
		private final int flagValue;
		private final RSDirection direction;

		/**
		 * Returns the mask direction of
		 * 
		 * @param first
		 * @param second
		 * @return
		 */
		public static Walk of(RSDirection direction) {
			return Stream.of(values()).filter(d -> d.direction == direction).findFirst().orElse(null);
		}
	}

	@RequiredArgsConstructor
	private enum Run {
		SouthWestSouthWest(0, SouthWest, SouthWest),
		SouthWestSouth(1, SouthWest, South),
		SouthSouth(2, South, South),
		SouthEastSouth(3, SouthEast, South),
		SouthEastSouthEast(4, SouthEast, SouthEast),
		SouthWestWest(5, SouthWest, West),
		SouthEastEast(6, SouthEast, East),
		WestWest(7, West, West),
		EastEast(8, East, East),
		NorthWestWest(9, NorthWest, West),
		NorthEastEast(10, NorthEast, East),
		NorthWestNorthWest(11, NorthWest, NorthWest),
		NorthWestNorth(12, NorthWest, North),
		NorthNorth(13, North, North),
		NorthEastNorth(14, NorthEast, North),
		NorthEastNorthEast(15, NorthEast, NorthEast);

		@Getter
		private final int flagValue;
		private final RSDirection first;
		private final RSDirection second;

		/**
		 * Returns the mask direction of
		 * 
		 * @param first
		 * @param second
		 * @return
		 */
		public static Run of(RSDirection first, RSDirection second) {
			return Stream.of(values()).filter(d -> (d.first == first && d.second == second) || (d.first == second && d.second == first)).findFirst().orElse(null);
		}
	}

}
