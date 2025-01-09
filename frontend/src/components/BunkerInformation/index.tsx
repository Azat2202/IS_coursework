import {RoomMessage} from "../../store/types.generated";

export function BunkerInformation({roomData}: {roomData?: RoomMessage}) {
    return <div>
        <p>{roomData?.cataclysm?.description}</p>
        <p>Вы находитесь в бункере в котором:</p>
        <ul>
            <li>Нужно остаться на: {roomData?.bunker?.stayDays} дней</li>
            <li>Запас еды на: {roomData?.bunker?.foodDays} дней</li>
            <li>Площадь бункера: {roomData?.bunker?.square} м²</li>
            <li>Количество людей помещающихся в бункере: {(roomData?.characters?.length ?? 0) / 2}</li>
            <li>Полезные вещи в бункере: {roomData?.bunker?.equipments?.reduce((p, n) => p + n.name, "")}</li>
        </ul>
    </div>
}