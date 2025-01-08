import {RoomMessage, useGetApiMeQuery} from "../../store/types.generated";

function UserFact(){
    return <div></div>
}

export function CharactersTable({roomData, canOpen}: { roomData?: RoomMessage, canOpen: boolean }) {
    const {data: user} = useGetApiMeQuery();

    return <div>
        <table>
            <thead>
            <tr>
                <td>Имя игрока</td>
                <td>Имя персонажа</td>
                <td>Телосложение</td>
                <td>Профессия</td>
                <td>Здоровье</td>
                <td>Хобби</td>
                <td>Особенность характера</td>
                <td>Инструмент</td>
                <td>Инвентарь</td>
            </tr>
            </thead>
            {roomData?.characters?.map(character =>
                <tr>
                    <td>{character.user?.login}</td>
                    <td>{character.name}</td>
                    <td>{character.openedFacts?.bodyType}</td>
                    <td>{character.openedFacts?.profession}</td>
                    <td>{character.openedFacts?.health}</td>
                    <td>{character.openedFacts?.hobby}</td>
                    <td>{character.openedFacts?.phobia}</td>
                    <td>{character.openedFacts?.trait}</td>
                    <td>{character.openedFacts?.equipment}</td>
                    <td>{character.openedFacts?.bag}</td>
                </tr>
            )}
        </table>

        {/*{roomData.characters?.find(c => c?.user?.id === user?.id).}*/}
    </div>
}