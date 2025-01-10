import {RoomMessage} from "../../store/types.generated";

export function BunkerInformation({roomData}: {roomData?: RoomMessage}) {
    return (
        <div className="bg-burgundy-950 p-6 rounded-lg shadow-[0_10px_10px_rgba(0,0,0,0.5),_0_-3px_10px_rgba(0,0,0,0.5)] shadow-burgundy-500/30">
            <p className="mb-4 text-lg text-burgundy-200">{roomData?.cataclysm?.description}</p>
            <h2 className="text-2xl font-semibold mb-4 text-burgundy-300">Информация о бункере:</h2>
            <ul className="space-y-2 text-burgundy-200">
                <li>Нужно остаться на: <span className="font-bold">{roomData?.bunker?.stayDays} дней</span></li>
                <li>Запас еды на: <span className="font-bold">{roomData?.bunker?.foodDays} дней</span></li>
                <li>Площадь бункера: <span className="font-bold">{roomData?.bunker?.square} м²</span></li>
                <li>Максимальная вместимость: <span className="font-bold">{(roomData?.characters?.length ?? 0) / 2} человек</span></li>
                <li>
                    Полезные вещи в бункере:{" "}
                    <span className="font-bold">{roomData?.bunker?.equipments?.map(e => e.name).join(", ") || "Нет"}</span>
                </li>
            </ul>
        </div>
    );
}